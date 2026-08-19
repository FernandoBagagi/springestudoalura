package br.com.ferdbgg.springestudoalura.model.api.request;

import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.model.enums.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizacaoMedico(

                @NotNull //
                Long id,

                @Email(message = "{formato.invalido.email}") //
                String email,

                String login,

                String senhaAntiga,
                String senhaNova,

                Genero genero,

                String nome,

                String sobrenome,

                @Pattern(regexp = "CRM\\/[A-Z]{2} \\d{4,6}", message = "{formato.invalido.crm}") //
                String crm,

                EspecialidadeMedico especialidade //

) {
}
