package br.com.ferdbgg.springestudoalura.dto.request;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.ferdbgg.springestudoalura.domain.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.validator.anotacoes.MinutosMultiploQuinze;
import br.com.ferdbgg.springestudoalura.validator.anotacoes.SegundosZerados;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosAgendamentoConsulta(

        @JsonAlias({"id_medico", "medico"})
        @Positive
        Long idMedico,

        EspecialidadeMedico especialidade,

        @NotNull
        @Positive
        Long idPaciente,

        @JsonAlias({"data_hora", "data/hora", "data"})
        @NotNull
        @MinutosMultiploQuinze
        @SegundosZerados
        OffsetDateTime dataHora

) {

}
