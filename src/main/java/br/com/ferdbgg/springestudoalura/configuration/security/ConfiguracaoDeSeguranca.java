package br.com.ferdbgg.springestudoalura.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ConfiguracaoDeSeguranca {

    private static final int TOKEN_VALIDITY_SECONDS = 5 * 60;

    @Value("${springestudoalura.configuration.security.key-remember-me}")
    private String keyRememberMe;

    private final FiltroDeSeguranca filtroDeSeguranca;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService, //
            PasswordEncoder passwordEncoder //
    ) {

        final var provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {

        return httpSecurity
                .csrf(this::csrf)
                .sessionManagement(this::statelessPolicy)
                .authorizeHttpRequests(this::authorize)
                .addFilterBefore(filtroDeSeguranca, beforeFilter())
                .formLogin(this::formLogin)
                .logout(this::logout)
                .rememberMe(this::rememberMe)
                .build();

    }

    private void csrf(CsrfConfigurer<HttpSecurity> configurer) {
        // Deixa o default
    }

    private SessionManagementConfigurer<HttpSecurity> statelessPolicy(
            SessionManagementConfigurer<HttpSecurity> configurer //
    ) {

        // Pro curso de segurança foi necessário tirar a política STATELESS
        return configurer; // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    }

    private void authorize(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth //
    ) {

        auth.requestMatchers(HttpMethod.POST, "/autenticacao").permitAll()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/web/assets/**", "/web/css/**", "/web/js/**").permitAll()
                .requestMatchers("/web/", "/web/index", "/web/home").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/medicos").hasAuthority("ATENDENTE")
                .requestMatchers(HttpMethod.DELETE, "/pacientes").hasAuthority("ATENDENTE")
                .anyRequest().authenticated();

    }

    private Class<? extends Filter> beforeFilter() {
        return UsernamePasswordAuthenticationFilter.class;
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
