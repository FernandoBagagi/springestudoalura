package br.com.ferdbgg.springestudoalura.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfiguracaoDeSegurancaApi {

    private final FiltroTokenApi filtroToken;

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChainApi(HttpSecurity httpSecurity) {

        return httpSecurity
                .securityMatcher("/api/**")
                .csrf(this::csrf)
                .sessionManagement(this::sessionManagement)
                .authorizeHttpRequests(this::authorizeHttpRequests)
                .addFilterBefore(filtroToken, filtroAutenticacaoPadrao())
                .build();

    }

    private void csrf(CsrfConfigurer<HttpSecurity> configurer) {

        configurer.disable(); // NOSONAR CSRF desabilitado na API

    }

    private SessionManagementConfigurer<HttpSecurity> sessionManagement(
            SessionManagementConfigurer<HttpSecurity> configurer //
    ) {

        return configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    private void authorizeHttpRequests(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth //
    ) {

        auth.requestMatchers(HttpMethod.POST, "/api/autenticacao").permitAll()
                .anyRequest().authenticated();

    }

    private Class<? extends Filter> filtroAutenticacaoPadrao() {

        return UsernamePasswordAuthenticationFilter.class;

    }

}
