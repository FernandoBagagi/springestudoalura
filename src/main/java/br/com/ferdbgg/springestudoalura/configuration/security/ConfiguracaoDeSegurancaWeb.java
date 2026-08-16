package br.com.ferdbgg.springestudoalura.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ConfiguracaoDeSegurancaWeb {

    private static final int TOKEN_VALIDITY_SECONDS = 5 * 60;

    @Value("${springestudoalura.configuration.security.key-remember-me}")
    private String keyRememberMe;

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChainWeb(HttpSecurity httpSecurity) {

        return httpSecurity
                .securityMatcher( //
                        "/swagger-ui.html", //
                        "/swagger-ui/**", //
                        "/v3/api-docs/**", //
                        "/web/**" //
                )
                .authorizeHttpRequests(this::authorize)
                .formLogin(this::formLogin)
                .logout(this::logout)
                .rememberMe(this::rememberMe)
                .build();

    }

    private void authorize(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth //
    ) {

        auth.requestMatchers("/swagger-ui.html", "/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/web/assets/**", "/web/css/**", "/web/js/**").permitAll()
                .requestMatchers("/web/", "/web/index", "/web/home").permitAll()
                .anyRequest().authenticated();

    }

    private void formLogin(FormLoginConfigurer<HttpSecurity> configurer) {

        configurer
                .loginPage("/web/login")
                .defaultSuccessUrl("/web/")
                .permitAll();

    }

    private void logout(LogoutConfigurer<HttpSecurity> configurer) {

        configurer
                .logoutUrl("/web/login/logout")
                .addLogoutHandler(new SecurityContextLogoutHandler())
                .logoutSuccessUrl("/web/login?logout")
                .permitAll();

    }

    private void rememberMe(RememberMeConfigurer<HttpSecurity> configurer) {

        configurer
                .key(keyRememberMe)
                .tokenValiditySeconds(TOKEN_VALIDITY_SECONDS);

    }

}
