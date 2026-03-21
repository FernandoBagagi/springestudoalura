package br.com.ferdbgg.springestudoalura.validator.consulta;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import br.com.ferdbgg.springestudoalura.dto.request.DadosAgendamentoConsulta;
import br.com.ferdbgg.springestudoalura.exception.AgendamentoConsultaException;
import br.com.ferdbgg.springestudoalura.util.DataHoraUtil;

@Component
public class ValidadorHorarioAgendamentoConsulta
        implements ValidadorAgendamentoConsulta {

    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        if (isDomingo(dados.dataHora())
                || isHoraClinicaFechada(dados.dataHora())) {

            throw new AgendamentoConsultaException();

        }

    }

    private boolean isDomingo(OffsetDateTime dataHora) {
        final DayOfWeek dia = dataHora.atZoneSameInstant(DataHoraUtil.BRASILIA).getDayOfWeek();
        return DayOfWeek.SUNDAY.equals(dia);
    }

    private boolean isHoraClinicaFechada(OffsetDateTime dataHora) {
        final int hora = DataHoraUtil.converterParaLocalTime(dataHora).getHour();
        return hora < 7 && 19 <= hora; // Último horário válido é 18h45
    }

}
