package br.com.ferdbgg.springestudoalura.component;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.ferdbgg.springestudoalura.entities.Usuario;
import br.com.ferdbgg.springestudoalura.repository.UsuarioRepository;
import br.com.ferdbgg.springestudoalura.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private static final String PREFIX = "Bearer ";

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    /**
     * Para criar um filtro usa implements jakarta.servlet.Filter
     * e @Override doFilter
     */

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain //
    ) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        autenticarUsuario(authorizationHeader);
        
        // Necessário pra continuar o fluxo de filtros
        filterChain.doFilter(request, response);
        
    }
    
    private void autenticarUsuario(final String authorizationHeader) {
        
        if (isAuthorizationHeaderInvalido(authorizationHeader)) {
            return;
        }

        final String token = authorizationHeader.strip().replaceFirst(PREFIX, "");

        final Long idUsuario = tokenService.validarToken(token);

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

        final UsernamePasswordAuthenticationToken authentication //
                = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

}
