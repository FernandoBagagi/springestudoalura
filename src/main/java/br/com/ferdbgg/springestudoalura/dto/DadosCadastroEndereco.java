package br.com.ferdbgg.springestudoalura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroEndereco(
                @NotBlank(message = "{obrigatorio.logradouro}") String logradouro,
                @NotBlank(message = "{obrigatorio.bairro") String bairro,
                @NotBlank(message = "{obrigatorio.cep}") @Pattern(regexp = "\\d{5}-\\d{3}", message = "{formato.invalido.cep}") String cep,
                @NotBlank(message = "{obrigatorio.cidade}") String cidade,
                @NotBlank(message = "{obrigatorio.uf}") String uf,
                String complemento,
                String numero) {
}
