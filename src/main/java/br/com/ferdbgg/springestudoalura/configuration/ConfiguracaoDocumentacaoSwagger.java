package br.com.ferdbgg.springestudoalura.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class ConfiguracaoDocumentacaoSwagger {

    public static final String BEARER_KEY = "bearer-key";

    @Bean
    public OpenAPI customOpenAPI() {

        final Components components = new Components()
                .addSecuritySchemes(BEARER_KEY, getSecurityScheme());

        return new OpenAPI().components(components).info(getInfo());

    }

    private SecurityScheme getSecurityScheme() {

        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

    }

    private Info getInfo() {

        return new Info()
                .title("Voll.med API")
                .description(
                        "API Rest da aplicação Voll.med, contendo as funcionalidades de CRUD de médicos e de pacientes, além de agendamento e cancelamento de consultas")
                .contact(getContact())
                .license(getLicense());

    }

    private Contact getContact() {
        return new Contact()
                .name("Time Backend")
                .email("backend@teste.spring");
    }

    private License getLicense() {
        return new License()
                .name("Apache 2.0")
                .url("http://voll.med/api/licenca");
    }

}
