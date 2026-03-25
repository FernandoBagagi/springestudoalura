package br.com.ferdbgg.springestudoalura.controller;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ferdbgg.springestudoalura.dto.request.DadosAtualizacaoMedicoPaciente;
import br.com.ferdbgg.springestudoalura.dto.request.DadosCadastroPaciente;
import br.com.ferdbgg.springestudoalura.dto.response.DadosBasicosPaciente;
import br.com.ferdbgg.springestudoalura.dto.response.DadosComplementaresPaciente;
import br.com.ferdbgg.springestudoalura.dto.response.Pagina;
import br.com.ferdbgg.springestudoalura.service.PacienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;

    @PostMapping
    public ResponseEntity<DadosBasicosPaciente> cadastrar(
            @RequestBody @Valid DadosCadastroPaciente dados,
            UriComponentsBuilder uriBuilder) {

        final DadosBasicosPaciente paciente = service.cadastrar(dados);

        final URI uri = uriBuilder
                .path("/pacientes/{id}")
                .buildAndExpand(paciente.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(paciente);

    }

    @GetMapping
    public ResponseEntity<Pagina<DadosBasicosPaciente>> listar(
            @PageableDefault(size = 50, sort = { "nome", "id" }) Pageable pageable) {

        final Pagina<DadosBasicosPaciente> pagina = service.listar(pageable);

        return ResponseEntity.ok(pagina);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosComplementaresPaciente> pesquisarPorId(@PathVariable Long id) {

        final DadosComplementaresPaciente paciente = service.pesquisarPorId(id);
        
        return ResponseEntity.ok(paciente);

    }

    @PutMapping
    public ResponseEntity<DadosBasicosPaciente> atualizarCadastro(
        @RequestBody @Valid DadosAtualizacaoMedicoPaciente dados) {
        
        final DadosBasicosPaciente paciente = service.atualizarCadastro(dados);

        return ResponseEntity.ok(paciente);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> inativarPorId(@PathVariable Long id) {

        service.inativarPorId(id);
    
        return ResponseEntity
                .noContent()
                .build();
    
    }

}
