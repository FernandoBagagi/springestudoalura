package br.com.ferdbgg.springestudoalura.validator.anotacoes.impl;

import java.time.LocalDateTime;

import br.com.ferdbgg.springestudoalura.validator.anotacoes.SegundosZerados;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SegundosZeradosImpl
        implements ConstraintValidator<SegundosZerados, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime dataHora, ConstraintValidatorContext context) {

        return dataHora.getSecond() == 0;

    }

}
