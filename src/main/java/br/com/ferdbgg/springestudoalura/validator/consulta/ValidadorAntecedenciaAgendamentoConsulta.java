package br.com.ferdbgg.springestudoalura.validator.consulta;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.dto.request.DadosAgendamentoConsulta;
import br.com.ferdbgg.springestudoalura.exception.AgendamentoConsultaException;

@Component
public class ValidadorAntecedenciaAgendamentoConsulta
        implements ValidadorAgendamentoConsulta {

    private static final int MINUTOS_ANTECEDENCIA = 30;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        if (dados == null) {
            throw new AgendamentoConsultaException();
        }

        final OffsetDateTime dataHoraComAntecedencia //
                = OffsetDateTime.now().plusMinutes(MINUTOS_ANTECEDENCIA);

        if (dataHoraComAntecedencia.isAfter(dados.dataHora())) {
            throw new AgendamentoConsultaException();
        }

    }

}
