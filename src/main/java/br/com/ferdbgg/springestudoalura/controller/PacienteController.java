package br.com.ferdbgg.springestudoalura.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ferdbgg.springestudoalura.dto.DadosCadastroPaciente;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    
    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroPaciente dados) {

    }
}
