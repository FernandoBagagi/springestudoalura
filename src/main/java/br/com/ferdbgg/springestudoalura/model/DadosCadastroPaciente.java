package br.com.ferdbgg.springestudoalura.model;

public record DadosCadastroPaciente(
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {
}
