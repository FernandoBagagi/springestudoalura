package br.com.ferdbgg.springestudoalura.model.api.request;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public record DadosFiltroConsulta(

                Long id,

                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
                LocalDate diaExato,

                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
                LocalDate diaInicio,

                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //
                LocalDate diaFim,

                @DateTimeFormat(pattern = "HH:mm") //
                LocalTime horaExata,

                @DateTimeFormat(pattern = "HH:mm") //
                LocalTime horaInicio,

                @DateTimeFormat(pattern = "HH:mm") //
                LocalTime horaFim,

                Long medicoId,

                String medicoNome,

                String medicoSobrenome,

                String medicoEspecialidade,

                String medicoCrm,

                Long pacienteId,

                String pacienteNome

) {

        public static DadosFiltroConsulta buildFromIds(Long medicoId, Long pacienteId) {

                return new DadosFiltroConsulta(
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                medicoId,
                                null,
                                null,
                                null,
                                null,
                                pacienteId,
                                null);

        }

}
