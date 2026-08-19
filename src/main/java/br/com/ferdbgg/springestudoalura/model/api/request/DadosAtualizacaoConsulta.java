package br.com.ferdbgg.springestudoalura.model.api.request;

import java.time.OffsetDateTime;

import br.com.ferdbgg.springestudoalura.validator.anotacoes.MinutosMultiploQuinze;
import br.com.ferdbgg.springestudoalura.validator.anotacoes.SegundosZerados;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosAtualizacaoConsulta(

                @NotNull //
                @Positive //
                Long id,

                @NotNull //
                @Positive //
                Long medicoId,

                @NotNull //
                @MinutosMultiploQuinze //
                @SegundosZerados //
                OffsetDateTime dataHora

) {

}
