package br.com.ferdbgg.springestudoalura.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
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
import br.com.ferdbgg.springestudoalura.dto.request.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.dto.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.dto.response.DadosComplementaresMedico;
import br.com.ferdbgg.springestudoalura.service.MedicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService service;

    @PostMapping
    public ResponseEntity<DadosBasicosMedico> cadastrar(
            @RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder) {

        final DadosBasicosMedico medico = service.cadastrar(dados);

        final URI uri = uriBuilder
                .path("/medicos/{id}")
                .buildAndExpand(medico.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(medico);

    }

    @GetMapping
    public ResponseEntity<Page<DadosBasicosMedico>> listar(
            @PageableDefault(sort = { "especialidade", "nome", "id" }) Pageable pageable) {

        // TODO: Criar página personalizada
        final Page<DadosBasicosMedico> page = service.listar(pageable);

        return ResponseEntity.ok(page);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosComplementaresMedico> pesquisarPorId(@PathVariable Long id) {

        final DadosComplementaresMedico medico = service.pesquisarPorId(id);

        return ResponseEntity.ok(medico);

    }

    @PutMapping
    public ResponseEntity<DadosBasicosMedico> atualizarCadastro(
            @RequestBody @Valid DadosAtualizacaoMedicoPaciente dados) {

        final DadosBasicosMedico medico = service.atualizarCadastro(dados);

        return ResponseEntity.ok(medico);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> inativarPorId(@PathVariable Long id) {

        service.inativarPorId(id);

        return ResponseEntity
                .noContent()
                .build();

    }

}
