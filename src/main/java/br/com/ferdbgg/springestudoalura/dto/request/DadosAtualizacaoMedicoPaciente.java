package br.com.ferdbgg.springestudoalura.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizacaoMedicoPaciente(
        @NotNull Long id,
        String nome,
        @Pattern(regexp = "\\d{2} \\d{4,5}-\\d{4}") String telefone,
        @Valid DadosCadastroEndereco endereco) {
}
