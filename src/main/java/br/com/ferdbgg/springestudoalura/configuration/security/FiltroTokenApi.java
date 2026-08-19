package br.com.ferdbgg.springestudoalura.configuration.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.ferdbgg.springestudoalura.model.entity.Usuario;
import br.com.ferdbgg.springestudoalura.repository.UsuarioRepository;
import br.com.ferdbgg.springestudoalura.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Para criar um filtro, basta implementar a interface
 * {@code jakarta.servlet.Filter} e sobrescrever o método {@code doFilter()},
 * responsável por interceptar e processar cada requisição antes que ela chegue
 * ao destino.
 *
 * Neste caso, foi utilizada a classe {@code OncePerRequestFilter}, que garante
 * que o filtro seja executado apenas uma vez para cada requisição HTTP,
 * evitando múltiplas execuções durante o mesmo ciclo de processamento da
 * requisição.
 */
@Component
@RequiredArgsConstructor
public class FiltroTokenApi extends OncePerRequestFilter {

    private static final String PREFIX = "Bearer ";

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, //
            HttpServletResponse response, //
            FilterChain filterChain //
    ) throws ServletException, IOException {

        final var authorizationHeader = request.getHeader("Authorization");
        autenticarUsuario(authorizationHeader);

        // Necessário pra continuar o fluxo de filtros
        filterChain.doFilter(request, response);

    }

    private void autenticarUsuario(String authorizationHeader) {

        if (isAuthorizationHeaderInvalido(authorizationHeader)) {
            return;
        }

        final var token = authorizationHeader.strip().replaceFirst(PREFIX, "");

        final var idUsuario = tokenService.validarToken(token);

        usuarioRepository
                .findById(idUsuario)
                .ifPresent(this::registrarAutenticacao);

    }

    private boolean isAuthorizationHeaderInvalido(String authorizationHeader) {

        return authorizationHeader == null
                || authorizationHeader.isBlank()
                || !authorizationHeader.startsWith(PREFIX);

    }

    private void registrarAutenticacao(Usuario usuario) {

        final var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

}
