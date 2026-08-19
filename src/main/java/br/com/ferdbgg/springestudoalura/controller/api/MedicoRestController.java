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

import br.com.ferdbgg.springestudoalura.model.api.request.DadosAtualizacaoMedico;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroMedico;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosBasicosMedico;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosCompletosMedico;
import br.com.ferdbgg.springestudoalura.model.api.response.Pagina;
import br.com.ferdbgg.springestudoalura.service.MedicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medicos")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class MedicoRestController {

    private final MedicoService service;

    @PostMapping
    public ResponseEntity<DadosBasicosMedico> cadastrar(
            @RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder //
    ) {

        final var medico = service.cadastrar(dados);

        final var uri = uriBuilder
                .path("/api/medicos/{id}")
                .buildAndExpand(medico.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(medico);

    }

    @GetMapping
    public ResponseEntity<Pagina<DadosBasicosMedico>> listar(
            @PageableDefault(size = 50, sort = { "especialidade", "nome", "id" }) Pageable pageable //
    ) {

        final var pagina = service.listarDadosBasicos(pageable);

        return ResponseEntity.ok(pagina);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosCompletosMedico> pesquisarPorId(@PathVariable Long id) {

        return service.pesquisarPorIdAndUsuarioAtivo(id, DadosCompletosMedico.class)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PutMapping
    public ResponseEntity<DadosBasicosMedico> atualizar(
            @RequestBody @Valid DadosAtualizacaoMedico dados //
    ) {

        final var medico = service.atualizar(dados);

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
