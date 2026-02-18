package br.com.ferdbgg.springestudoalura.dto;

public record DadosCadastroPaciente(
        String nome,
        String email,
        String telefone,
        String cpf,
        EnderecoDTO endereco) {
}
