package br.com.ferdbgg.springestudoalura.exception;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorExcecoes {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> entidadeNaoEncotrada() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<CampoMensagem>> campoInvalido(MethodArgumentNotValidException e) {

        List<CampoMensagem> erros = e
                .getFieldErrors()
                .stream()
                .map(f -> new CampoMensagem(f.getField(), f.getDefaultMessage()))
                .toList();

        return ResponseEntity
                .badRequest()
                .body(erros);

    }

    @ExceptionHandler(AgendamentoConsultaException.class)
    public ResponseEntity<Map<String, String>> agendamentoConsultaInvalido(AgendamentoConsultaException e) {

        return ResponseEntity
                .badRequest()
                .body(Map.of("erro", e.getMessage()));

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> tratarErro400(HttpMessageNotReadableException e) {

        return ResponseEntity
                .badRequest()
                .body(Map.of("erro", e.getMessage()));

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> tratarErroBadCredentials() {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("erro", "Credenciais inválidas"));

    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> tratarErroAuthentication() {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("erro", "Falha na autenticação"));

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> tratarErroAcessoNegado() {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of("erro", "Acesso negado"));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> tratarErro500(Exception ex) {

        ex.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("erro", ex.getLocalizedMessage()));

    }

}
