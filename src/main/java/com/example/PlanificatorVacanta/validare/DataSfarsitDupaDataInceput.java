/** Clasa folosita ca adnotare, pentru a verifica daca data de sfarsit a unei vacante
 * este situata cronologic dupa data de inceput a acesteia
 * @author Nume Student
 * @version 23 Decembrie 2024
 */

package com.example.PlanificatorVacanta.validare;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = DataSfarsitDupaDataInceputValidator.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface DataSfarsitDupaDataInceput {

    String message() default "Data de sfarsit nu poate fi înaintea datei de inceput.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
