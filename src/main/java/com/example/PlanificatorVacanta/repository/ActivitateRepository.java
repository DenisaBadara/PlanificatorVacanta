/** Clasa de tip interfata folosita pentru a cauta o activitate in functie de id-ul vacantei
 * @author Bădără Denisa
 * @version 27 Decembrie 2024
 */
package com.example.PlanificatorVacanta.repository;
import com.example.PlanificatorVacanta.model.Activitate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivitateRepository extends JpaRepository<Activitate, Integer> {
    List<Activitate> findByVacanta_Id(Integer id);
}
