package br.com.ferdbgg.springestudoalura.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ferdbgg.springestudoalura.dto.DadosCadastroPaciente;
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
        service.cadastrarNovoPaciente(dados);
    }
}
