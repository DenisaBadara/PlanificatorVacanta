/** Clasa de tip interfata care permite cautarea utilizatorului dupa username
 * @author Bădără Denisa
 * @version 27 Decembrie 2024
 */

package com.example.PlanificatorVacanta.repository;
import com.example.PlanificatorVacanta.model.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Utilizator, Integer> {
    Utilizator findByUsername(String username);
}
