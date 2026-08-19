package br.com.ferdbgg.springestudoalura.model.exception;

public class TokenException extends RuntimeException {

    private TokenException(String message, Throwable cause) {
        super(message, cause);
    }

    // Padrão Static Factory Method
    public static TokenException erroGeracao (Throwable cause) {
        return new TokenException("Token não gerado", cause);
    }

    public static TokenException erroValidacao (Throwable cause) {
        return new TokenException("Token inválido", cause);
    }

}
