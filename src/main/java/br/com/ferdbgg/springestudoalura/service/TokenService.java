package br.com.ferdbgg.springestudoalura.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.ferdbgg.springestudoalura.domain.entity.Usuario;
import br.com.ferdbgg.springestudoalura.exception.TokenException;

@Service
public class TokenService {

    private static final String CLAIM_ID = "id";
    private static final long SEGUNDOS_VALIDADE_TOKEN = 2L * 60L * 60L; // 2h
    private static final String ISSUER = "Spring Estudo Alura";

    @Value("${springestudoalura.token.jwt.secret-key}")
    private String secret;

    private Algorithm getAlgoritmo() {
        return Algorithm.HMAC256(secret);
    }

    public String tentarGerarToken(Usuario usuario) throws TokenException {

        final var agora = Instant.now();
        final var validade = agora.plusSeconds(SEGUNDOS_VALIDADE_TOKEN);
        
        try {
            
            return JWT.create()
                    .withClaim(CLAIM_ID, usuario.getId())
                    .withSubject(usuario.getLogin())
                    .withIssuedAt(agora)
                    .withExpiresAt(validade)
                    .withIssuer(ISSUER)
                    .sign(getAlgoritmo());

        } catch (Exception e) {

            throw TokenException.erroGeracao(e);

        }

    }

    public Long validarToken(String token) throws TokenException {
        
        try {
            
            final var verificador = JWT.require(getAlgoritmo())
                    .withIssuer(ISSUER)
                    .build();

            final var jwtDecodificado = verificador.verify(token);
            
            return jwtDecodificado.getClaim(CLAIM_ID).asLong();
        
        } catch (Exception e) {
            
            throw TokenException.erroValidacao(e);

        }
    }

}
