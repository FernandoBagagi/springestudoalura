package br.com.ferdbgg.springestudoalura.validator.anotacoes.impl;

import java.time.LocalDateTime;

import br.com.ferdbgg.springestudoalura.validator.anotacoes.MinutosMultiploQuinze;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MinutosMultiploQuinzeImpl
        implements ConstraintValidator<MinutosMultiploQuinze, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime dataHora, ConstraintValidatorContext context) {

        return dataHora.getMinute() % 15 == 0;

    }

}
