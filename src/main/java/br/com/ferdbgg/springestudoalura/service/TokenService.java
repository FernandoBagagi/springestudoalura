package br.com.ferdbgg.springestudoalura.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import br.com.ferdbgg.springestudoalura.dto.DadosResponseToken;
import br.com.ferdbgg.springestudoalura.entities.Usuario;
import br.com.ferdbgg.springestudoalura.exception.TokenException;

@Service
public class TokenService {

    private static final String CLAIM_ID = "id";
    private static final long SEGUNDOS_VALIDADE_TOKEN = 2L * 60L * 60L; // 2h
    private static final String ISSUER = "Spring Estudo Alura";

    @Value("${springestudoalura.token.jwt.secret-key}")
    private String secret;

    public DadosResponseToken tentarGerarToken(Usuario usuario) throws TokenException {

        final Instant agora = Instant.now();
        
        try {
            
            final Algorithm algorithm = Algorithm.HMAC256(secret);

            final String token = JWT.create()
                    .withClaim(CLAIM_ID, usuario.getId())
                    .withSubject(usuario.getLogin())
                    .withIssuedAt(agora)
                    .withExpiresAt(agora.plusSeconds(SEGUNDOS_VALIDADE_TOKEN))
                    .withIssuer(ISSUER)
                    .sign(algorithm);

            return new DadosResponseToken(token);

        } catch (JWTCreationException e) {

            throw TokenException.erroGeracao(e);

        }

    }

    public Long validarToken(String token) throws TokenException {
        
        try {
            
            final Algorithm algorithm = Algorithm.HMAC256(secret);
            
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();

            final DecodedJWT decodedJWT = verifier.verify(token);
            
            return decodedJWT.getClaim(CLAIM_ID).asLong();
        
        } catch (JWTVerificationException e) {
            
            throw TokenException.tokenInvalido(e);

        }
    }

}
