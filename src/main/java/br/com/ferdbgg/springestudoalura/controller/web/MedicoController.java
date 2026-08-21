package br.com.ferdbgg.springestudoalura.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import br.com.ferdbgg.springestudoalura.model.entity.Medico;
import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.model.enums.Genero;
import br.com.ferdbgg.springestudoalura.model.mapper.MedicoMapper;
import br.com.ferdbgg.springestudoalura.model.web.form.CadastroEdicaoMedicoForm;
import br.com.ferdbgg.springestudoalura.service.MedicoService;

@Controller
@RequestMapping("/web/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private static final String FORM = "form";
    private static final String PAGINA_LISTAGEM = "medico/listagem-medicos";
    private static final String PAGINA_CADASTRO = "medico/formulario-medico";
    private static final String REDIRECT_LISTAGEM = "redirect:/web/medicos?sucesso";

    private final MedicoMapper mapper;
    private final MedicoService service;

    @ModelAttribute("generos")
    public Genero[] generos() {

        return Genero.values();

    }

    @ModelAttribute("especialidades")
    public EspecialidadeMedico[] especialidades() {

        return EspecialidadeMedico.values();

    }

    @GetMapping
    public String carregarPaginaListagem( //
            @PageableDefault Pageable paginacao, //
            Model model //
    ) {

        final var pagina = service.listarDadosBasicos(paginacao);

        model.addAttribute("pagina", pagina);

        return PAGINA_LISTAGEM;

    }

    @GetMapping("formulario")
    @PreAuthorize("hasAuthority('ATENDENTE') OR hasAuthority('MEDICO')")
    public String carregarPaginaCadastro(Long id, Model model) {

        final var dados = service
                .pesquisarPorIdAndUsuarioAtivo(id, Medico.class);

        final var form = dados.isPresent()
                ? mapper.parseCadastroEdicaoForm(dados.get())
                : CadastroEdicaoMedicoForm.empty();

        model.addAttribute(FORM, form);

        return PAGINA_CADASTRO;

    }

    @PostMapping
    @PreAuthorize("hasAuthority('ATENDENTE')")
    public String cadastrar( //
            @Valid @ModelAttribute(FORM) CadastroEdicaoMedicoForm form, //
            BindingResult result, //
            Model model //
    ) {

        if (result.hasErrors()) {

            model.addAttribute(FORM, form);

            return PAGINA_CADASTRO;

        }

        try {

            if (form.isCadastro()) {
                service.cadastrar(mapper.parseDadosCadastro(form));
            } else {
                service.atualizar(mapper.parseDadosAtualizacao(form));
            }

            return REDIRECT_LISTAGEM;

        } catch (RuntimeException e) {

            model.addAttribute("erro", e.getMessage());
            model.addAttribute(FORM, form);

            return PAGINA_CADASTRO;

        }

    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ATENDENTE')")
    public String excluir(Long id) {

        service.inativarPorId(id);

        return REDIRECT_LISTAGEM;

    }

}
