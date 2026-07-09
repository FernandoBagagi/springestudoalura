package br.com.ferdbgg.springestudoalura.controller.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ferdbgg.springestudoalura.domain.dto.form.CadastroPacienteForm;
import br.com.ferdbgg.springestudoalura.domain.dto.request.DadosAtualizacaoMedicoPaciente;
import br.com.ferdbgg.springestudoalura.domain.dto.request.DadosCadastroEndereco;
import br.com.ferdbgg.springestudoalura.domain.dto.request.DadosCadastroPaciente;
import br.com.ferdbgg.springestudoalura.service.PacienteService;

@Controller
@RequestMapping("/web/pacientes")
public class PacienteController {

    private static final String DADOS = "dados";
    private static final String PAGINA_LISTAGEM = "paciente/listagem-pacientes";
    private static final String PAGINA_CADASTRO = "paciente/formulario-paciente";
    private static final String REDIRECT_LISTAGEM = "redirect:/web/pacientes?sucesso";

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    public String carregarPaginaListagem( //
            @PageableDefault Pageable paginacao, //
            Model model //
    ) {
        var pacientesCadastrados = service.listar(paginacao);
        model.addAttribute("pacientes", pacientesCadastrados);
        return PAGINA_LISTAGEM;
    }

    @GetMapping("formulario")
    public String carregarPaginaCadastro(Long id, Model model) {

        final CadastroPacienteForm dados = id == null
                ? new CadastroPacienteForm(
                        0L,
                        "",
                        "",
                        "",
                        "")
                : service.pesquisarDadosFormPorId(id);

        model.addAttribute(DADOS, dados);

        return PAGINA_CADASTRO;
    }

    @PostMapping
    public String cadastrar( //
            @Valid @ModelAttribute(DADOS) CadastroPacienteForm form, //
            BindingResult result, //
            Model model //
    ) {

        final DadosCadastroPaciente novosDados = new DadosCadastroPaciente(
                form.nome(),
                form.email(),
                form.telefone(),
                form.cpf(),
                new DadosCadastroEndereco(
                        "Avenida Paulista",
                        "Bela Vista",
                        "01311-000",
                        "São Paulo",
                        "SP",
                        "Sala 1203",
                        "1578"));

        if (result.hasErrors()) {
            model.addAttribute(DADOS, form);
            return PAGINA_CADASTRO;
        }

        try {
            if (form.hasIdValido()) {
                service.atualizarCadastro(
                        new DadosAtualizacaoMedicoPaciente( //
                                form.id(),
                                novosDados.nome(),
                                novosDados.telefone(),
                                novosDados.endereco()) //
                );
            } else {
                service.cadastrar(novosDados);
            }
            return REDIRECT_LISTAGEM;
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute(DADOS, form);
            return PAGINA_CADASTRO;
        }
    }

    @DeleteMapping
    public String excluir(Long id) {
        service.inativarPorId(id);
        return REDIRECT_LISTAGEM;
    }

}
