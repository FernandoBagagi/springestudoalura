package br.com.ferdbgg.springestudoalura.validator.anotacoes.impl;

import java.time.OffsetDateTime;

import br.com.ferdbgg.springestudoalura.validator.anotacoes.SegundosZerados;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SegundosZeradosImpl
        implements ConstraintValidator<SegundosZerados, OffsetDateTime> {

    @Override
    public boolean isValid(OffsetDateTime dataHora, ConstraintValidatorContext context) {

        return dataHora.getSecond() == 0;

    }

}
