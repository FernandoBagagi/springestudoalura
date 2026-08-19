package br.com.ferdbgg.springestudoalura.model.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroEndereco(

        @NotBlank(message = "{obrigatorio.logradouro}") //
        String logradouro,

        String numero,

        String complemento,

        @NotBlank(message = "{obrigatorio.bairro") //
        String bairro,

        @NotBlank(message = "{obrigatorio.cidade}") //
        String cidade,

        @NotBlank(message = "{obrigatorio.uf}") //
        @Pattern(regexp = "[A-Z]{2}", message = "{formato.invalido.uf}")
        String uf,

        @NotBlank(message = "{obrigatorio.cep}") //
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "{formato.invalido.cep}") //
        String cep

) {
}
