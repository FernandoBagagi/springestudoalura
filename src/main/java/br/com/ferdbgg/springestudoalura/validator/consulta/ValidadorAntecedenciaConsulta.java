package br.com.ferdbgg.springestudoalura.validator.consulta;

import java.time.Clock;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.model.api.request.DadosCadastroConsulta;
import br.com.ferdbgg.springestudoalura.model.exception.AgendamentoConsultaException;

@Component
public class ValidadorAntecedenciaConsulta
        implements ValidadorCadastroConsulta {

    private static final int MINUTOS_ANTECEDENCIA = 30;

    @Override
    public void validar(DadosCadastroConsulta dados) {

        final var dataHoraComAntecedencia = OffsetDateTime
                .now(Clock.systemDefaultZone())
                .plusMinutes(MINUTOS_ANTECEDENCIA);

        if (dataHoraComAntecedencia.isAfter(dados.dataHora())) {

            throw AgendamentoConsultaException.antecedenciaMinima(MINUTOS_ANTECEDENCIA);

        }

    }

}
