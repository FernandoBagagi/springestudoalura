package br.com.ferdbgg.springestudoalura.model.api.request;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroPaciente(

                @NotBlank(message = "{obrigatorio.email}") //
                @Email(message = "{formato.invalido.email}") //
                String email,

                @NotBlank(message = "{obrigatorio.login}") //
                String login,

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

                @NotNull(message = "{obrigatorio.endereco}") //
                @Valid //
                DadosCadastroEndereco endereco

) {
}
