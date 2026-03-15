package br.com.ferdbgg.springestudoalura.validator;

import java.time.OffsetDateTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidadorDiaHoraConsulta
        implements ConstraintValidator<DiaHoraConsultaValido, OffsetDateTime> {

    @Override
    public boolean isValid(OffsetDateTime dataHora, ConstraintValidatorContext context) {

        return isDataNoFuturo(dataHora) 
        && isMinutosMultiploQuinze(dataHora.getMinute())
        && isSegundosZerados(dataHora.getSecond());

    }

    boolean isDataNoFuturo(OffsetDateTime data) {
        return OffsetDateTime.now().isBefore(data);
    }

    boolean isMinutosMultiploQuinze(int minutos) {
        return minutos % 15 == 0;
    }

    boolean isSegundosZerados(int segundos) {
        return segundos == 0;
    }

}
