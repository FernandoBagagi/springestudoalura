package br.com.ferdbgg.springestudoalura.dto;

public record DadosBasicosMedico(
        Long id,
        String nome,
        String crm,
        String especialidade) {
}
