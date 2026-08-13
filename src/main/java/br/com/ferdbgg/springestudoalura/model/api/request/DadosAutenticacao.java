package br.com.ferdbgg.springestudoalura.model.api.request;

import jakarta.validation.constraints.NotNull;

public record DadosAutenticacao(
                @NotNull String login,
                @NotNull String senha //
) {
}
