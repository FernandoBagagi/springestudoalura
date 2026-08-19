package br.com.ferdbgg.springestudoalura.model.api.request;

import jakarta.validation.constraints.Pattern;

public record DadosAtualizacaoEndereco(

        String logradouro,

        String numero,

        String complemento,

        String bairro,

        String cidade,

        @Pattern(regexp = "[A-Z]{2}", message = "{formato.invalido.uf}")
        String uf,

        @Pattern(regexp = "\\d{5}-\\d{3}", message = "{formato.invalido.cep}") //
        String cep

) {
}
