package br.com.ferdbgg.springestudoalura.domain.dto.request;

import jakarta.validation.constraints.NotNull;

public record DadosAutenticacaoUsuario(
        @NotNull String username,
        @NotNull String senha) {
}
