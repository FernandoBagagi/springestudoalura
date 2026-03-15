package br.com.ferdbgg.springestudoalura.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * A data/hora deve ser um {@code OffsetDateTime} que esteja no futuro, tenha os minutos
 * múltiplo de 15 e os segundos estejam zerados.

 * Elementos {@code null} são considerados válidos.
 *
 * @author Fernando Bagagi
 * @since 0.0
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorDiaHoraConsulta.class)
public @interface DiaHoraConsultaValido {

    String message() default "Horário de consulta inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
