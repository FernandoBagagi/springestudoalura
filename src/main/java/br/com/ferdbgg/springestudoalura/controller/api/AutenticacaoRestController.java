package br.com.ferdbgg.springestudoalura.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosAutenticacao;
import br.com.ferdbgg.springestudoalura.model.api.response.DadosToken;
import br.com.ferdbgg.springestudoalura.model.entity.Usuario;
import br.com.ferdbgg.springestudoalura.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/autenticacao")
@RequiredArgsConstructor
public class AutenticacaoRestController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<DadosToken> autenticar(@RequestBody @Valid DadosAutenticacao dados) {

        final var tokenAutenticacao = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

        final var autenticacao = authenticationManager.authenticate(tokenAutenticacao);

        final var usuario = (Usuario) autenticacao.getPrincipal();

        final var tokenGerado = tokenService.tentarGerarToken(usuario);

        final var response = new DadosToken(tokenGerado);

        return ResponseEntity.ok(response);

    }

}
