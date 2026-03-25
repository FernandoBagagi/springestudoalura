package br.com.ferdbgg.springestudoalura.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public record DadosFiltroConsulta(

        Long id,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dia,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate diaInicio,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate diaFim,

        @DateTimeFormat(pattern = "HH:mm") LocalTime hora,

        @DateTimeFormat(pattern = "HH:mm") LocalTime horaInicio,
        @DateTimeFormat(pattern = "HH:mm") LocalTime horaFim,

        Long medicoId,
        String medicoNome,
        String medicoEspecialidade,
        String medicoCrm,

        Long pacienteId,
        String pacienteNome

) {

}
