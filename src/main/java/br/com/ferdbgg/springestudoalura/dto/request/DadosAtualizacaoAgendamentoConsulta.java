package br.com.ferdbgg.springestudoalura.dto.request;

import java.time.OffsetDateTime;

import br.com.ferdbgg.springestudoalura.validator.DiaHoraConsultaValido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosAtualizacaoAgendamentoConsulta(
        @NotNull @Positive Long id,
        @NotNull @Positive Long idMedico,
        @NotNull @DiaHoraConsultaValido OffsetDateTime dataHora) {

}
