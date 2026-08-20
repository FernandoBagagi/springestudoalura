package br.com.ferdbgg.springestudoalura.model.web.form;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.validator.anotacoes.MinutosMultiploQuinze;
import br.com.ferdbgg.springestudoalura.validator.anotacoes.SegundosZerados;
import jakarta.validation.constraints.Positive;

public record CadastroEdicaoConsultaForm(

        @Positive //
        Long id,

        @Positive //
        Long medicoId,

        EspecialidadeMedico medicoEspecialidade,

        @Positive //
        Long pacienteId,

        //@MinutosMultiploQuinze //
        //@SegundosZerados //
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") //
        LocalDateTime dataHora

) {

    public boolean isCadastro() {

        return id == null || id == 0L;

    }

    public static CadastroEdicaoConsultaForm empty() {

        return new CadastroEdicaoConsultaForm(
                null,
                null,
                null,
                null,
                null);

    }

}
