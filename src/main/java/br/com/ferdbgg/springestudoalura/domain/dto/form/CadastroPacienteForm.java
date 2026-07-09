package br.com.ferdbgg.springestudoalura.domain.dto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CadastroPacienteForm(
                Long id,
                @NotBlank String nome,
                @NotBlank @Email String email,
                @NotBlank @Pattern(regexp = "\\d{2} \\d{4,5}-\\d{4}") String telefone,
                @NotBlank @Pattern(regexp = "\\d{3}.\\d{3}.\\d{3}-\\d{2}") String cpf) {

        public boolean hasIdValido() {
                return id != null && id > 0L;
        }
}
