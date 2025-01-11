/** Clasa folosita ca si validator, pentru a valida corectidudinea introducerii
 * datei de sfarsit in baza de date, implicit in formularul de adaugare / editare
 * pentru o vacanta
 * @author Bădără Denisa
 * @version 23 Decembrie 2024
 */


package com.example.PlanificatorVacanta.validare;
import com.example.PlanificatorVacanta.model.Vacanta;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DataSfarsitDupaDataInceputValidator implements ConstraintValidator<DataSfarsitDupaDataInceput, Vacanta> {

    @Override
    public void initialize(DataSfarsitDupaDataInceput constraintAnnotation) {
    }

    @Override
    public boolean isValid(Vacanta vacanta, ConstraintValidatorContext context) {
        if (vacanta == null) {
            return true;
        }

        LocalDate dataInceput = vacanta.getDataInceput();
        LocalDate dataSfarsit = vacanta.getDataSfarsit();

        if (dataInceput == null || dataSfarsit == null) {
            return true;
        }

        return !dataSfarsit.isBefore(dataInceput);
    }
}
