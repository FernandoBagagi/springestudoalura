package br.com.ferdbgg.springestudoalura.validator.anotacoes.impl;

import java.time.OffsetDateTime;

import br.com.ferdbgg.springestudoalura.validator.anotacoes.MinutosMultiploQuinze;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MinutosMultiploQuinzeImpl
        implements ConstraintValidator<MinutosMultiploQuinze, OffsetDateTime> {

    @Override
    public boolean isValid(OffsetDateTime dataHora, ConstraintValidatorContext context) {

        return dataHora.getMinute() % 15 == 0;

    }

}
