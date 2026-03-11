package br.com.ferdbgg.springestudoalura.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ferdbgg.springestudoalura.dto.request.DadosAtualizacaoMedicoPaciente;
import br.com.ferdbgg.springestudoalura.dto.request.DadosCadastroPaciente;
import br.com.ferdbgg.springestudoalura.dto.response.DadosBasicosPaciente;
import br.com.ferdbgg.springestudoalura.dto.response.DadosComplementaresPaciente;
import br.com.ferdbgg.springestudoalura.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService service;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dados) {
        service.cadastrar(dados);
    }

    @GetMapping
    public Page<DadosBasicosPaciente> listar(
            @PageableDefault(size = 50, sort = { "nome", "id" }) Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public DadosComplementaresPaciente pesquisarPorId(@PathVariable Long id) {
        return service.pesquisarPorId(id);
    }

    @PutMapping
    public void atualizarCadastro(@RequestBody @Valid DadosAtualizacaoMedicoPaciente dados) {
        service.atualizarCadastro(dados);
    }

    @DeleteMapping("/{id}")
    public void inativarPorId(@PathVariable Long id) {
        service.inativarPorId(id);
    }

}
