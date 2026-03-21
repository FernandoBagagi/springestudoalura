package br.com.ferdbgg.springestudoalura.validator.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.ferdbgg.springestudoalura.validator.anotacoes.impl.MinutosMultiploQuinzeImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * A data/hora deve ser um {@code OffsetDateTime} que tenha os minutos múltiplo
 * de 15.
 * 
 * Elementos {@code null} são considerados válidos.
 *
 * @author Fernando Bagagi
 * @since 0.0
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinutosMultiploQuinzeImpl.class)
public @interface MinutosMultiploQuinze {

    String message() default "Os minutos do horário devem ser múltiplo de 15.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
