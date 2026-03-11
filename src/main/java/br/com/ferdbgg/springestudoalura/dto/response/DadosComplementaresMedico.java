package br.com.ferdbgg.springestudoalura.dto.response;

import br.com.ferdbgg.springestudoalura.entities.Endereco;

public record DadosComplementaresMedico(
        String email,
        String telefone,
        Endereco endereco) {
}
