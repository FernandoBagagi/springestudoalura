package br.com.ferdbgg.springestudoalura.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ferdbgg.springestudoalura.model.entity.Paciente;
import br.com.ferdbgg.springestudoalura.model.mapper.PacienteMapper;
import br.com.ferdbgg.springestudoalura.model.web.form.CadastroEdicaoPacienteForm;
import br.com.ferdbgg.springestudoalura.service.PacienteService;

@Controller
@RequestMapping("/web/pacientes")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ATENDENTE')")
public class PacienteController {

    private static final String FORM = "form";
    private static final String PAGINA_LISTAGEM = "paciente/listagem-pacientes";
    private static final String PAGINA_CADASTRO = "paciente/formulario-paciente";
    private static final String REDIRECT_LISTAGEM = "redirect:/web/pacientes?sucesso";

    private final PacienteMapper mapper;
    private final PacienteService service;

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
    public String carregarPaginaCadastro(Long id, Model model) {

        final var dados = service
                .pesquisarPorIdAndUsuarioAtivo(id, Paciente.class);

        final var form = dados.isPresent()
                ? mapper.parseCadastroEdicaoForm(dados.get())
                : CadastroEdicaoPacienteForm.empty();

        model.addAttribute(FORM, form);

        return PAGINA_CADASTRO;

    }

    @PostMapping
    public String cadastrar( //
            @Valid @ModelAttribute(FORM) CadastroEdicaoPacienteForm form, //
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
    public String excluir(Long id) {

        service.inativarPorId(id);

        return REDIRECT_LISTAGEM;

    }

}
