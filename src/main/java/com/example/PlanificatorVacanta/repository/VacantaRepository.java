/** Clasa de tip interfata folosita pentru implementarea validatorului de data, paginarea in
 * functie de buget, cautarea vacantei dupa numele tarii, gasirea paginii in functie de buget si
 * gasirea vacantelor in functie de filtrul de buget
 * @author Bădără Denisa
 * @version 27 Decembrie 2024
 */

package com.example.PlanificatorVacanta.repository;
import com.example.PlanificatorVacanta.model.Vacanta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VacantaRepository extends JpaRepository<Vacanta, Integer> {
    @Documented
    @Constraint(validatedBy = DataSfarsitValidator.class)
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface DataSfarsitValida {
        String message() default "Data de sfârșit trebuie să fie după data de început";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    @Component
    class DataSfarsitValidator implements ConstraintValidator<DataSfarsitValida, Vacanta> {

        @Override
        public boolean isValid(Vacanta vacanta, ConstraintValidatorContext context) {
            LocalDate dataInceput = vacanta.getDataInceput();
            LocalDate dataSfarsit = vacanta.getDataSfarsit();

            if (dataInceput != null && dataSfarsit != null) {
                if (dataSfarsit.isBefore(dataInceput)) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("Data de sfârșit trebuie să fie după data de început.")
                            .addConstraintViolation();
                    return false;
                }
            }
            return true;
        }
    }

    // Vacante cu buget mai mic sau egal decat o valoare specificata
    List<Vacanta> findByBugetTotalLessThanEqual(Double maxBuget);

    // Vacante cu buget mai mare sau egal decat o valoare specificata
    List<Vacanta> findByBugetTotalGreaterThanEqual(Double minBuget);

    // Vacante cu buget intre doua valori specificate
    List<Vacanta> findByBugetTotalBetween(Double minBuget, Double maxBuget);

    Page<Vacanta> findByBugetTotalBetween(Double minBuget, Double maxBuget, Pageable pageable);
    Page<Vacanta> findByBugetTotalGreaterThanEqual(Double minBuget, Pageable pageable);
    Page<Vacanta> findByBugetTotalLessThanEqual(Double maxBuget, Pageable pageable);

    List<Vacanta> findBynumeTara(String countryName);

}


