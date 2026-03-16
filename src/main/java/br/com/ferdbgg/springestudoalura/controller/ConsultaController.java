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

import br.com.ferdbgg.springestudoalura.dto.request.DadosAgendamentoConsulta;
import br.com.ferdbgg.springestudoalura.dto.request.DadosAtualizacaoAgendamentoConsulta;
import br.com.ferdbgg.springestudoalura.dto.response.DadosConsulta;
import br.com.ferdbgg.springestudoalura.service.ConsultaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService service;

    @PostMapping
    public void agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        service.agendar(dados);
    }

    @GetMapping
    public Page<DadosConsulta> listar(
            // TODO: ver como transformar isso numa consulta mais completa
            @PageableDefault(sort = { "id" }) Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public DadosConsulta pesquisarPorId(@PathVariable Long id) {
        return service.pesquisarPorId(id);
    }

    @PutMapping
    public void atualizarAgendamento(
            @RequestBody @Valid DadosAtualizacaoAgendamentoConsulta dados) {
        service.atualizarAgendamento(dados);
    }

    @DeleteMapping("/{id}")
    public void cancelarAgendamentoPorId(@PathVariable Long id) {
        service.cancelarAgendamentoPorId(id);
    }

}
