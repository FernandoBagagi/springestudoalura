package br.com.ferdbgg.springestudoalura.domain.dto.response;

import br.com.ferdbgg.springestudoalura.domain.entity.Endereco;

public record DadosComplementaresMedico(
        String email,
        String telefone,
        Endereco endereco) {
}
