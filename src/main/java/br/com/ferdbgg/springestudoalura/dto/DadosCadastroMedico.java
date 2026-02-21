package br.com.ferdbgg.springestudoalura.dto;

import br.com.ferdbgg.springestudoalura.model.EspecialidadeMedico;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(
                @NotBlank String nome,
                @NotBlank @Email String email,
                @NotBlank @Pattern(regexp = "CRM\\/[A-Z]{2} \\d{4,6}") String crm,
                @NotNull EspecialidadeMedico especialidade,
                @NotNull @Valid DadosCadastroEndereco endereco) {
}
