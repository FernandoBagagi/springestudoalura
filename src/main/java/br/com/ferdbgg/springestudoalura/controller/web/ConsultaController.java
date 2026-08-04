package br.com.ferdbgg.springestudoalura.controller.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import br.com.ferdbgg.springestudoalura.domain.dto.request.DadosAgendamentoConsulta;
import br.com.ferdbgg.springestudoalura.domain.dto.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.domain.entity.Usuario;
import br.com.ferdbgg.springestudoalura.domain.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.exception.AgendamentoConsultaException;
import br.com.ferdbgg.springestudoalura.service.ConsultaService;
import br.com.ferdbgg.springestudoalura.service.MedicoService;

@Controller
@RequestMapping("/web/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private static final String DADOS = "dados";
    private static final String PAGINA_LISTAGEM = "consulta/listagem-consultas";
    private static final String PAGINA_CADASTRO = "consulta/formulario-consulta";
    private static final String REDIRECT_LISTAGEM = "redirect:/consultas?sucesso";

    private final ConsultaService service;
    private final MedicoService medicoService;

    @ModelAttribute("especialidades")
    public EspecialidadeMedico[] especialidades() {
        return EspecialidadeMedico.values();
    }

    @ModelAttribute("medicos")
    public List<DadosBasicosMedico> medicos() {
        return medicoService.listar(null).conteudo();
    }

    @GetMapping
    public String carregarPaginaListagem(
            @PageableDefault Pageable paginacao, //
            Model model, //
            @AuthenticationPrincipal Usuario usuarioLogado //
    ) {

        final var medicoId = usuarioLogado.isMedico() ? usuarioLogado.getId() : null;
        final var pacienteId = usuarioLogado.isPaciente() ? usuarioLogado.getId() : null;

        final var filtro = service.buildFiltrofromIDs(medicoId, pacienteId);
        final var consultasAtivas = service.listar(filtro, paginacao);
        model.addAttribute("consultas", consultasAtivas);
        return PAGINA_LISTAGEM;
    }

    @GetMapping("formulario")
    @PreAuthorize("hasAuthority('ATENDENTE') OR " +
            "(hasAuthority('PACIENTE') AND (#id == null OR @consultaService.pesquisarDadosAgendamentoConsultaPorId(#id).idPaciente == authentication.principal.id))")
    public String carregarPaginaAgendaConsulta(Long id, Model model) {

        final DadosAgendamentoConsulta dados = id == null
                ? new DadosAgendamentoConsulta(null, null, 0L, OffsetDateTime.now(Clock.systemDefaultZone()))
                : service.pesquisarDadosAgendamentoConsultaPorId(id); // TODO: ver esse idPaciente da linha de cima e ajeitar o cadastro de consulta

        model.addAttribute(DADOS, dados);

        return PAGINA_CADASTRO;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ATENDENTE') OR " +
            "(hasAuthority('PACIENTE') AND #dados.idPaciente == authentication.principal.id)")
    public String cadastrar(
            @Valid @ModelAttribute(DADOS) DadosAgendamentoConsulta dados, //
            BindingResult result, //
            Model model //
    ) {
        if (result.hasErrors()) {
            model.addAttribute(DADOS, dados);
            return PAGINA_CADASTRO;
        }

        try {
            service.agendar(dados);
            return REDIRECT_LISTAGEM;
        } catch (AgendamentoConsultaException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute(DADOS, dados);
            return PAGINA_CADASTRO;
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ATENDENTE') OR " +
            "(hasAuthority('PACIENTE') AND @consultaService.pesquisarDadosAgendamentoConsultaPorId(#id).idPaciente == authentication.principal.id) OR "
            +
            "(hasAuthority('MEDICO') AND @consultaService.pesquisarDadosAgendamentoConsultaPorId(#id).idMedico == authentication.principal.id)")
    public String excluir(Long id) {
        service.cancelarAgendamentoPorId(id);
        return REDIRECT_LISTAGEM;
    }

}
