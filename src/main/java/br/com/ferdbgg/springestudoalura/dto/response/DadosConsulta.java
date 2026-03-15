package br.com.ferdbgg.springestudoalura.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ferdbgg.springestudoalura.util.DataHoraUtil;

public record DadosConsulta(

        Long id,
        @JsonIgnore LocalDate dia,
        @JsonIgnore LocalTime hora,

        Long medicoId,
        String medicoNome,
        String medicoEspecialidade,
        String medicoCrm,

        Long pacienteId,
        String pacienteNome

) {

    public OffsetDateTime dataHora() {

        return DataHoraUtil
                .converterParaOffsetDateTime(dia, hora);
                
    }

}
