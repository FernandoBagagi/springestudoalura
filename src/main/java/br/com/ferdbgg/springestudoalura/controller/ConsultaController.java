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

import br.com.ferdbgg.springestudoalura.dto.request.DadosAgendamentoConsulta;
import br.com.ferdbgg.springestudoalura.dto.request.DadosAtualizacaoAgendamentoConsulta;
import br.com.ferdbgg.springestudoalura.dto.response.DadosConsulta;
import br.com.ferdbgg.springestudoalura.service.ConsultaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService service;

    @PostMapping
    public ResponseEntity<DadosConsulta> agendar(
            @RequestBody @Valid DadosAgendamentoConsulta dados,
            UriComponentsBuilder uriBuilder) {
        
        final DadosConsulta dadosConsulta = service.agendar(dados);

        final URI uri = uriBuilder
                .path("/consultas/{id}")
                .buildAndExpand(dadosConsulta.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(dadosConsulta);
    
    }

    @GetMapping
    public Page<DadosConsulta> listar(
            // TODO: ver como transformar isso numa consulta mais completa
            @PageableDefault(sort = { "id" }) Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosConsulta> pesquisarPorId(@PathVariable Long id) {

        final DadosConsulta dadosConsulta = service.pesquisarPorId(id);
        
        return ResponseEntity.ok(dadosConsulta);

    }

    @PutMapping
    public void atualizarAgendamento(
            @RequestBody @Valid DadosAtualizacaoAgendamentoConsulta dados) {
        service.atualizarAgendamento(dados);
    }

    @DeleteMapping("/{id}")
    public void cancelarAgendamentoPorId(@PathVariable Long id) {
        // TODO: padronizar para devolver response entity
        // return ResponseEntity.noContent().build()
        service.cancelarAgendamentoPorId(id);

        // TODO: public enum MotivoCancelamento
        // {PACIENTE_DESISTIU,MEDICO_CANCELOU,OUTROS;}
        // TODO: @Column(name = "motivo_cancelamento") @Enumerated(EnumType.STRING)
        // private MotivoCancelamento motivoCancelamento;
        // TODO: alter table consultas add column motivo_cancelamento varchar(100);
    }

}
