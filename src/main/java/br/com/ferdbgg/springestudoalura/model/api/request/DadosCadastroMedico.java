package br.com.ferdbgg.springestudoalura.model.api.request;

import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.model.enums.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(

                @NotBlank(message = "{obrigatorio.email}") //
                @Email(message = "{formato.invalido.email}") //
                String email,

                @NotBlank(message = "{obrigatorio.login}") //
                String login,

                @NotBlank(message = "{obrigatorio.genero}") //
                Genero genero,

                @NotBlank(message = "{obrigatorio.nome}") //
                String nome,

                @NotBlank(message = "{obrigatorio.sobrenome}") //
                String sobrenome,

                @NotBlank(message = "{obrigatorio.crm}") //
                @Pattern(regexp = "CRM\\/[A-Z]{2} \\d{4,6}", message = "{formato.invalido.crm}") //
                String crm,

                @NotNull(message = "{obrigatorio.especialidade}") //
                EspecialidadeMedico especialidade //

) {
}
