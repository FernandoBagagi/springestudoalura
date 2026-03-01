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

import br.com.ferdbgg.springestudoalura.dto.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.dto.DadosAtualizacao;
import br.com.ferdbgg.springestudoalura.dto.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.dto.DadosComplementaresMedico;
import br.com.ferdbgg.springestudoalura.service.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService service;

    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        service.cadastrar(dados);
    }

    @GetMapping
    public Page<DadosBasicosMedico> listar(
            @PageableDefault(sort = { "especialidade", "nome", "id" }) Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public DadosComplementaresMedico pesquisarPorId(@PathVariable Long id) {
        return service.pesquisarPorId(id);
    }
    
    @PutMapping
    public void atualizarCadastro(@RequestBody @Valid DadosAtualizacao dados) {
        service.atualizarCadastro(dados);
    }

    @DeleteMapping("/{id}")
    public void inativarPorId(@PathVariable Long id) {
        service.inativarPorId(id);
    }

}
