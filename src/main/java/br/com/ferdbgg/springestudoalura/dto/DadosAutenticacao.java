package br.com.ferdbgg.springestudoalura.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAutenticacao(
        @NotNull String username,
        @NotNull String senha) {
}
