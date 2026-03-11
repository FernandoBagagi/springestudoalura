package br.com.ferdbgg.springestudoalura.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ferdbgg.springestudoalura.dto.request.DadosAutenticacaoUsuario;
import br.com.ferdbgg.springestudoalura.dto.response.DadosResponseToken;
import br.com.ferdbgg.springestudoalura.entities.Usuario;
import br.com.ferdbgg.springestudoalura.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/autenticacao")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<Object> autenticar(@RequestBody @Valid DadosAutenticacaoUsuario dados) {

        final UsernamePasswordAuthenticationToken authentication //
                = new UsernamePasswordAuthenticationToken(dados.username(), dados.senha());

        final Authentication tokenAuthentication = manager.authenticate(authentication);

        final Usuario usuario = (Usuario) tokenAuthentication.getPrincipal();

        final DadosResponseToken token = tokenService.tentarGerarToken(usuario);

        return ResponseEntity.ok(token);

    }

}
