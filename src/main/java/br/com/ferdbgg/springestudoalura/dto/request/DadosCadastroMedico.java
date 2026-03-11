package br.com.ferdbgg.springestudoalura.dto.request;

import br.com.ferdbgg.springestudoalura.entities.EspecialidadeMedico;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(
        @NotBlank(message = "{obrigatorio.nome}") String nome,
        @NotBlank(message = "{obrigatorio.email}") @Email(message = "{formato.invalido.email}") String email,
        @NotBlank(message = "{obrigatorio.telefone}") @Pattern(regexp = "\\d{2} \\d{4,5}-\\d{4}", message = "{formato.invalido.telefone}") String telefone,
        @NotBlank(message = "{obrigatorio.crm}") @Pattern(regexp = "CRM\\/[A-Z]{2} \\d{4,6}", message = "{formato.invalido.crm}") String crm,
        @NotNull(message = "{obrigatorio.especialidade}") EspecialidadeMedico especialidade,
        @NotNull(message = "{obrigatorio.endereco}") @Valid DadosCadastroEndereco endereco) {
}
