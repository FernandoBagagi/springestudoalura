package br.com.ferdbgg.springestudoalura.dto.request;

import jakarta.validation.constraints.NotNull;

public record DadosAutenticacaoUsuario(
        @NotNull String username,
        @NotNull String senha) {
}
