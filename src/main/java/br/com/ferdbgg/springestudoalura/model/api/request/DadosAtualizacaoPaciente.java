package br.com.ferdbgg.springestudoalura.model.api.request;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizacaoPaciente(

                @NotNull //
                Long id,

                @Email(message = "{formato.invalido.email}") //
                String email,

                String login,

                String senhaAntiga,
                String senhaNova,

                String nome,

                @Pattern(regexp = "\\d{3}.\\d{3}.\\d{3}-\\d{2}", message = "{formato.invalido.cpf}") //
                String cpf,

                LocalDate nascimento,

                @Pattern(regexp = "\\d{2} \\d{4,5}-\\d{4}", message = "{formato.invalido.telefone}") //
                String telefone,

                @Valid //
                DadosAtualizacaoEndereco endereco

) {
}
