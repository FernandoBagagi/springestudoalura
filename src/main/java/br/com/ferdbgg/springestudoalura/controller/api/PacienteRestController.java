package br.com.ferdbgg.springestudoalura.controller.api;

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

import br.com.ferdbgg.springestudoalura.model.api.request.DadosAtualizacaoPaciente;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroPaciente;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosBasicosPaciente;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosCompletosPaciente;
import br.com.ferdbgg.springestudoalura.model.api.response.Pagina;
import br.com.ferdbgg.springestudoalura.service.PacienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class PacienteRestController {

    private final PacienteService service;

    @PostMapping
    public ResponseEntity<DadosBasicosPaciente> cadastrar(
            @RequestBody @Valid DadosCadastroPaciente dados,
            UriComponentsBuilder uriBuilder //
    ) {

        final var paciente = service.cadastrar(dados);

        final var uri = uriBuilder
                .path("/api/pacientes/{id}")
                .buildAndExpand(paciente.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(paciente);

    }

    @GetMapping
    public ResponseEntity<Pagina<DadosBasicosPaciente>> listar(
            @PageableDefault(size = 50, sort = { "nome", "id" }) Pageable pageable //
    ) {

        final var pagina = service.listarDadosBasicos(pageable);

        return ResponseEntity.ok(pagina);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosCompletosPaciente> pesquisarPorId(@PathVariable Long id) {

        return service.pesquisarPorIdAndUsuarioAtivo(id, DadosCompletosPaciente.class)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PutMapping
    public ResponseEntity<DadosBasicosPaciente> atualizar(
            @RequestBody @Valid DadosAtualizacaoPaciente dados //
    ) {

        final var paciente = service.atualizar(dados);

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
