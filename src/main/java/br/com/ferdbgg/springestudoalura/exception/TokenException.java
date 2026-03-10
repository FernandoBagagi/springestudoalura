package br.com.ferdbgg.springestudoalura.exception;

import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {

    private final String message; 
    private final Throwable cause;

    private TokenException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    public static TokenException erroGeracao (Throwable cause) {
        return new TokenException("Erro ao gerar token", cause);
    }

    public static TokenException tokenInvalido (Throwable cause) {
        return new TokenException("Token inválido", cause);
    }

    public static TokenException erroEnvio () {
        return new TokenException("Token não enviado no cabeçalho", null);
    }

}
