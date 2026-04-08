package br.com.ferdbgg.springestudoalura.controller.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import br.com.ferdbgg.springestudoalura.domain.entity.Medico;
import br.com.ferdbgg.springestudoalura.domain.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.dto.request.DadosCadastroEndereco;
import br.com.ferdbgg.springestudoalura.dto.request.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.dto.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.dto.response.Pagina;
import br.com.ferdbgg.springestudoalura.exception.AgendamentoConsultaException;
import br.com.ferdbgg.springestudoalura.service.MedicoService;

@Controller
@RequestMapping("/web/medicos")
public class MedicoAction2 {

    private static final String PAGINA_LISTAGEM = "medico/listagem-medicos";
    private static final String PAGINA_CADASTRO = "medico/formulario-medico";
    private static final String REDIRECT_LISTAGEM = "redirect:/web/medicos?sucesso";

    private final MedicoService service;

    public MedicoAction2(MedicoService service) {
        this.service = service;
    }

    @ModelAttribute("especialidades")
    public EspecialidadeMedico[] especialidades() {
        return EspecialidadeMedico.values();
    }

    @GetMapping
    public String carregarPaginaListagem(@PageableDefault Pageable paginacao, Model model) {
        var medicosCadastrados = service.listar(paginacao);
        model.addAttribute("medicos", medicosCadastrados);
        return PAGINA_LISTAGEM;
    }

    @GetMapping("formulario")
    public String carregarPaginaCadastro(Long id, Model model) {

        final Medico dados = id == null
                ? new Medico(null, null, null, null, null, null, null, null)
                : service.pesquisarMedicoPorId(id);

        model.addAttribute("dados", dados);

        return PAGINA_CADASTRO;
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute("dados") DadosCadastroMedico dados, BindingResult result,
            Model model) {

        final DadosCadastroMedico novosDados = new DadosCadastroMedico(
                dados.nome(),
                dados.email(),
                dados.telefone(),
                dados.crm(),
                dados.especialidade(),
                new DadosCadastroEndereco(
                        "Avenida Paulista",
                        "Bela Vista",
                        "01311-000",
                        "São Paulo",
                        "SP",
                        "Sala 1203",
                        "1578"));

        if (result.hasErrors()) {
            model.addAttribute("dados", novosDados);
            return PAGINA_CADASTRO;
        }

        try {
            service.cadastrar(novosDados);
            return REDIRECT_LISTAGEM;
        } catch (AgendamentoConsultaException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("dados", novosDados);
            return PAGINA_CADASTRO;
        }
    }

    @DeleteMapping
    public String excluir(Long id) {
        service.inativarPorId(id);
        return REDIRECT_LISTAGEM;
    }

    @GetMapping("{especialidade}")
    @ResponseBody
    public Pagina<DadosBasicosMedico> listarMedicosPorEspecialidade(@PageableDefault Pageable paginacao,
            @PathVariable String especialidade) {
        // return service.listarPorEspecialidade(Especialidade.valueOf(especialidade));
        return service.listar(paginacao);
    }

}
