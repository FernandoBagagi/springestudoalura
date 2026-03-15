package br.com.ferdbgg.springestudoalura.dto.request;

import java.time.OffsetDateTime;

import br.com.ferdbgg.springestudoalura.domain.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.validator.DiaHoraConsultaValido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosAgendamentoConsulta(
                @Positive Long idMedico,
                EspecialidadeMedico especialidade,
                @NotNull @Positive Long idPaciente,
                @NotNull @DiaHoraConsultaValido OffsetDateTime dataHora) {

}
