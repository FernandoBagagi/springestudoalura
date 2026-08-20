package br.com.ferdbgg.springestudoalura.model.web.form;

import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.model.enums.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CadastroEdicaoMedicoForm(

        Long id,

        @NotBlank(message = "{obrigatorio.email}") //
        @Email(message = "{formato.invalido.email}") //
        String usuarioEmail,

        @NotBlank(message = "{obrigatorio.login}") //
        String usuarioLogin,

        String senhaAntiga,

        String senhaNova,

        @NotNull(message = "{obrigatorio.genero}") //
        Genero genero,

        @NotBlank(message = "{obrigatorio.nome}") //
        String nome,

        @NotBlank(message = "{obrigatorio.sobrenome}") //
        String sobrenome,

        @NotBlank(message = "{obrigatorio.crm}") //
        @Pattern(regexp = "CRM\\/[A-Z]{2} \\d{4,6}", message = "{formato.invalido.crm}") //
        String crm,

        @NotNull(message = "{obrigatorio.especialidade}") //
        EspecialidadeMedico especialidade

) {

    public boolean isCadastro() {

        return id == null || id == 0L;

    }

    public static CadastroEdicaoMedicoForm empty() {
        
        return new CadastroEdicaoMedicoForm(
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
