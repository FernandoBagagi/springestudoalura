package br.com.ferdbgg.springestudoalura.validator;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;

import br.com.ferdbgg.springestudoalura.util.DataHoraUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorDiaHoraConsulta
        implements ConstraintValidator<DiaHoraConsultaValido, OffsetDateTime> {

    @Override
    public boolean isValid(OffsetDateTime dataHora, ConstraintValidatorContext context) {

        return hasAntecedenciaTrintaMinutos(dataHora)
                && isDiaBetweenSegundaAndSabado(dataHora)
                && isHoraBetweenSeteAndDezenove(dataHora)
                && isMinutosMultiploQuinze(dataHora.getMinute())
                && isSegundosZerados(dataHora.getSecond());

    }

    private boolean hasAntecedenciaTrintaMinutos(OffsetDateTime data) {
        return OffsetDateTime.now().plusMinutes(30).isBefore(data);
    }

    private boolean isDiaBetweenSegundaAndSabado(OffsetDateTime dataHora) {
        final DayOfWeek dia = dataHora.atZoneSameInstant(DataHoraUtil.BRASILIA).getDayOfWeek();
        return !DayOfWeek.SUNDAY.equals(dia);
    }

    private boolean isHoraBetweenSeteAndDezenove(OffsetDateTime dataHora) {
        final int hora = DataHoraUtil.converterParaLocalTime(dataHora).getHour();
        return 7 <= hora && hora < 19; //Último horário válido é 18h45
    }

    private boolean isMinutosMultiploQuinze(int minutos) {
        return minutos % 15 == 0;
    }

    boolean isSegundosZerados(int segundos) {
        return segundos == 0;
    }

}
