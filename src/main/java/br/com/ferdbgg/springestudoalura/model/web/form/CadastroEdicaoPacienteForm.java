package br.com.ferdbgg.springestudoalura.model.web.form;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CadastroEdicaoPacienteForm(

        Long id,

        @NotBlank(message = "{obrigatorio.email}") //
        @Email(message = "{formato.invalido.email}") //
        String usuarioEmail,

        @NotBlank(message = "{obrigatorio.login}") //
        String usuarioLogin,

        String senhaAntiga,

        String senhaNova,

        @NotBlank(message = "{obrigatorio.nome}") //
        String nome,

        @NotBlank(message = "{obrigatorio.cpf}") //
        @Pattern(regexp = "\\d{3}.\\d{3}.\\d{3}-\\d{2}", message = "{formato.invalido.cpf}") //
        String cpf,

        @NotBlank(message = "{obrigatorio.nascimento}") //
        LocalDate nascimento,

        @NotBlank(message = "{obrigatorio.telefone}") //
        @Pattern(regexp = "\\d{2} \\d{4,5}-\\d{4}", message = "{formato.invalido.telefone}") //
        String telefone,

        @NotBlank(message = "{obrigatorio.logradouro}") //
        String enderecoLogradouro,

        String enderecoNumero,

        String enderecoComplemento,

        @NotBlank(message = "{obrigatorio.bairro") //
        String enderecoBairro,

        @NotBlank(message = "{obrigatorio.cidade}") //
        String enderecoCidade,

        @NotBlank(message = "{obrigatorio.uf}") //
        @Pattern(regexp = "[A-Z]{2}", message = "{formato.invalido.uf}")
        String enderecoUf,

        @NotBlank(message = "{obrigatorio.cep}") //
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "{formato.invalido.cep}") //
        String enderecoCep

) {

    public boolean isCadastro() {

        return id == null || id == 0L;

    }

    public static CadastroEdicaoPacienteForm empty() {

        return new CadastroEdicaoPacienteForm(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

    }

}
