package br.com.ferdbgg.springestudoalura.controller.web;

import jakarta.validation.Valid;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import br.com.ferdbgg.springestudoalura.domain.entity.Medico;
import br.com.ferdbgg.springestudoalura.domain.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.dto.request.DadosAgendamentoConsulta;
import br.com.ferdbgg.springestudoalura.dto.request.DadosFiltroConsulta;
import br.com.ferdbgg.springestudoalura.dto.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.exception.AgendamentoConsultaException;
import br.com.ferdbgg.springestudoalura.service.ConsultaService;
import br.com.ferdbgg.springestudoalura.service.MedicoService;

@Controller
@RequestMapping("/web/consultas")
public class ConsultaAction {

    private static final String DADOS = "dados";
    private static final String PAGINA_LISTAGEM = "consulta/listagem-consultas";
    private static final String PAGINA_CADASTRO = "consulta/formulario-consulta";
    private static final String REDIRECT_LISTAGEM = "redirect:/consultas?sucesso";

    private final ConsultaService service;
    private final MedicoService medicoService;

    public ConsultaAction(ConsultaService consultaService, MedicoService medicoService) {
        this.service = consultaService;
        this.medicoService = medicoService;
    }

    @ModelAttribute("especialidades")
    public EspecialidadeMedico[] especialidades() {
        return EspecialidadeMedico.values();
    }

    @ModelAttribute("medicos")
    public List<DadosBasicosMedico> medicos() {
        return medicoService.listar(null).conteudo();
    }

    @GetMapping
    public String carregarPaginaListagem(@PageableDefault Pageable paginacao, Model model) {
        var filtro = new DadosFiltroConsulta(null, null, null, null, null, null, null, null, null, null, null, null,
                null);
        var consultasAtivas = service.listar(filtro, paginacao);
        model.addAttribute("consultas", consultasAtivas);
        return PAGINA_LISTAGEM;
    }

    @GetMapping("formulario")
    public String carregarPaginaAgendaConsulta(Long id, Model model) {

        final DadosAgendamentoConsulta dados = id == null
                ? new DadosAgendamentoConsulta(null, null, 0L, OffsetDateTime.now())
                : service.pesquisarDadosAgendamentoConsultaPorId(id);

        model.addAttribute(DADOS, dados);

        return PAGINA_CADASTRO;
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute(DADOS) DadosAgendamentoConsulta dados, BindingResult result,
            Model model) {
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
    public String excluir(Long id) {
        service.cancelarAgendamentoPorId(id);
        return REDIRECT_LISTAGEM;
    }

}
