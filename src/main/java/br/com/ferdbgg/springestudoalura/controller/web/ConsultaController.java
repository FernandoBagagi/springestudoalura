package br.com.ferdbgg.springestudoalura.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosFiltroConsulta;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosBasicosPaciente;
import br.com.ferdbgg.springestudoalura.model.entity.Consulta;
import br.com.ferdbgg.springestudoalura.model.entity.Usuario;
import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.model.mapper.ConsultaMapper;
import br.com.ferdbgg.springestudoalura.model.web.form.CadastroEdicaoConsultaForm;
import br.com.ferdbgg.springestudoalura.service.ConsultaService;
import br.com.ferdbgg.springestudoalura.service.MedicoService;
import br.com.ferdbgg.springestudoalura.service.PacienteService;

@Controller
@RequestMapping("/web/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private static final String FORM = "form";
    private static final String PAGINA_LISTAGEM = "consulta/listagem-consultas";
    private static final String PAGINA_CADASTRO = "consulta/formulario-consulta";
    private static final String REDIRECT_LISTAGEM = "redirect:/web/consultas?sucesso";

    private final ConsultaMapper mapper;
    private final ConsultaService consultaService;
    private final MedicoService medicoService;
    private final PacienteService pacienteService;

    @ModelAttribute("especialidades")
    public EspecialidadeMedico[] especialidades(
            @AuthenticationPrincipal Usuario usuarioLogado //
    ) {

        return usuarioLogado.isMedico()
                ? new EspecialidadeMedico[0]
                : EspecialidadeMedico.values();

    }

    @ModelAttribute("medicos")
    public List<DadosBasicosMedico> medicos(
            @AuthenticationPrincipal Usuario usuarioLogado //
    ) {

        return usuarioLogado.isMedico()
                ? medicoService.listarDadosBasicosPorIdAndUsuarioAtivo(usuarioLogado.getId())
                : medicoService.listarTodosDadosBasicos();

    }

    @ModelAttribute("pacientes")
    public List<DadosBasicosPaciente> pacientes(
            @AuthenticationPrincipal Usuario usuarioLogado //
    ) {

        return usuarioLogado.isPaciente()
                ? pacienteService.listarDadosBasicosPorIdAndUsuarioAtivo(usuarioLogado.getId())
                : pacienteService.listarTodosDadosBasicos();

    }

    @GetMapping
    public String carregarPaginaListagem(
            @PageableDefault(size = 5, sort = { "dia", "hora" }, direction = Direction.DESC) Pageable paginacao,
            Model model,
            @AuthenticationPrincipal Usuario usuarioLogado //
    ) {

        final var medicoId = usuarioLogado.isMedico() ? usuarioLogado.getId() : null;
        final var pacienteId = usuarioLogado.isPaciente() ? usuarioLogado.getId() : null;

        final var filtro = DadosFiltroConsulta.buildFromIds(medicoId, pacienteId);

        final var pagina = consultaService.listar(filtro, paginacao);

        model.addAttribute("pagina", pagina);

        return PAGINA_LISTAGEM;

    }

    @GetMapping("formulario")
    public String carregarPaginaCadastro(
            Long id,
            Model model,
            @AuthenticationPrincipal Usuario usuarioLogado //
    ) {

        final var dados = consultaService
                .pesquisarPorIdAndUsuarioId(
                        id,
                        usuarioLogado.getId(),
                        usuarioLogado.getPerfil(),
                        Consulta.class);

        final CadastroEdicaoConsultaForm form = dados.isPresent()
                ? mapper.parseCadastroEdicaoForm(dados.get())
                : CadastroEdicaoConsultaForm.empty();

        model.addAttribute(FORM, form);

        return PAGINA_CADASTRO;
    }

    @PostMapping
    public String cadastrar(
            @Valid @ModelAttribute(FORM) CadastroEdicaoConsultaForm form,
            BindingResult result,
            Model model,
            @AuthenticationPrincipal Usuario usuarioLogado //
    ) {

        if (result.hasErrors()) {

            model.addAttribute(FORM, form);

            return PAGINA_CADASTRO;

        }

        try {

            if (form.isCadastro()) {
                consultaService.cadastrar(mapper.parseDadosCadastro(form));
            } else {
                consultaService.atualizar(
                        mapper.parseDadosAtualizacao(form),
                        usuarioLogado.getId(),
                        usuarioLogado.getPerfil());
            }

            return REDIRECT_LISTAGEM;

        } catch (RuntimeException e) {

            model.addAttribute("erro", e.getMessage());
            model.addAttribute(FORM, form);

            return PAGINA_CADASTRO;

        }

    }

    @DeleteMapping
    public String excluir(
            Long id,
            @AuthenticationPrincipal Usuario usuarioLogado //
    ) {

        consultaService
                .deletarPorId(id, usuarioLogado.getId(), usuarioLogado.getPerfil());

        return REDIRECT_LISTAGEM;

    }

}
