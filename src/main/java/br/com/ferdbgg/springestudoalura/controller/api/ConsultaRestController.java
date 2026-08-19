package br.com.ferdbgg.springestudoalura.controller.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosAtualizacaoConsulta;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroConsulta;
import br.com.ferdbgg.springestudoalura.model.api.request.DadosFiltroConsulta;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosConsulta;
import br.com.ferdbgg.springestudoalura.model.api.response.Pagina;
import br.com.ferdbgg.springestudoalura.service.ConsultaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/consultas")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class ConsultaRestController {

    private final ConsultaService service;

    @PostMapping
    public ResponseEntity<DadosConsulta> cadastrar(
            @RequestBody @Valid DadosCadastroConsulta dados,
            UriComponentsBuilder uriBuilder //
    ) {

        final var consulta = service.cadastrar(dados);

        final var uri = uriBuilder
                .path("/api/consultas/{id}")
                .buildAndExpand(consulta.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(consulta);

    }

    @GetMapping
    public ResponseEntity<Pagina<DadosConsulta>> listar(
            @ModelAttribute DadosFiltroConsulta filtro,
            @PageableDefault(size = 50, sort = { "dia", "hora" }) Pageable pageable //
    ) {

        final var pagina = service.listar(filtro, pageable);

        return ResponseEntity.ok(pagina);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosConsulta> pesquisarPorId(@PathVariable Long id) {

        return service.pesquisarPorId(id, DadosConsulta.class)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PutMapping
    public ResponseEntity<DadosConsulta> atualizar(
            @RequestBody @Valid DadosAtualizacaoConsulta dados //
            ) {

        final var consulta = service.atualizar(dados);

        return ResponseEntity.ok(consulta);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPorId(@PathVariable Long id) {

        service.deletarPorId(id);

        return ResponseEntity
                .noContent()
                .build();

    }

}
