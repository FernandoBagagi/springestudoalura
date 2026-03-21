package br.com.ferdbgg.springestudoalura.validator.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.ferdbgg.springestudoalura.validator.anotacoes.impl.SegundosZeradosImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * A data/hora deve ser um {@code OffsetDateTime} que tenha os segundos zerados.

 * Elementos {@code null} são considerados válidos.
 *
 * @author Fernando Bagagi
 * @since 0.0
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SegundosZeradosImpl.class)
public @interface SegundosZerados {

    String message() default "A hora deve ter os segundos zerados";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
