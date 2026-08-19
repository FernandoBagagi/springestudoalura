package br.com.ferdbgg.springestudoalura.model.api.request;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.ferdbgg.springestudoalura.model.enums.EspecialidadeMedico;
import br.com.ferdbgg.springestudoalura.validator.anotacoes.MinutosMultiploQuinze;
import br.com.ferdbgg.springestudoalura.validator.anotacoes.SegundosZerados;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosCadastroConsulta(

                @JsonAlias({
                                "id_medico",
                                "medico" }) //
                @Positive //
                Long medicoId,

                EspecialidadeMedico especialidade, //

                @NotNull //
                @Positive //
                Long pacienteId,

                @JsonAlias({
                                "data_hora",
                                "data/hora",
                                "data" }) //
                @NotNull //
                @MinutosMultiploQuinze //
                @SegundosZerados //
                OffsetDateTime dataHora

        ) {

}
