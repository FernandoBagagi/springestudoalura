package br.com.ferdbgg.springestudoalura.model;

public record DadosCadastroMedico(
        String nome,
        String email,
        String crm,
        EspecialidadeMedico especialidade,
        Endereco endereco) {
}
