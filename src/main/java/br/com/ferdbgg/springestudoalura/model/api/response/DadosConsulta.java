package br.com.ferdbgg.springestudoalura.model.api.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.model.enums.Genero;
import br.com.ferdbgg.springestudoalura.model.mapper.MedicoMapper;
import br.com.ferdbgg.springestudoalura.util.DataHoraUtil;

public record DadosConsulta(

        Long id,

        @JsonIgnore //
        LocalDate dia,

        @JsonIgnore //
        LocalTime hora,

        Long medicoId,

        EspecialidadeMedico medicoEspecialidade,

        @JsonIgnore //
        Genero medicoGenero,

        @JsonIgnore //
        String medicoNome,

        @JsonIgnore //
        String medicoSobrenome,

        String medicoCrm,

        Long pacienteId,

        String pacienteNome

) {

    public OffsetDateTime dataHora() {

        return DataHoraUtil
                .converterParaOffsetDateTime(dia, hora);

    }

    @Override
    public String medicoNome() {

        return MedicoMapper
                .buildMedicoNome(medicoGenero, medicoNome, medicoSobrenome);

    }

}
