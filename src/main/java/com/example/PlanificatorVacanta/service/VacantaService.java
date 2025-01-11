/** Clasa de tip interfata folosita pentru a defini functiile din VacantaServiceImpl,
 * pentru a genera functionalitati legate de tabelul Vacanta, precum: salvarea, stergerea,
 * gasirea id-ului vacantei, gasirea obiectelor dupa numele tarii si paginarea in functie
 * de filtrul de buget
 * @author Bădără Denisa
 * @version 23 Decembrie 2024
 */

package com.example.PlanificatorVacanta.service;
import com.example.PlanificatorVacanta.model.Vacanta;
import org.springframework.data.domain.Page;
import java.util.List;

public interface VacantaService {

    List<Vacanta> getAllVacances();

    void saveVacanta(Vacanta vacanta);

    Vacanta getVacantabyId(int id);

    void deleteVacancebyId(int id);

    Page<Vacanta> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);

    public Page<Vacanta> findPaginatedByBugetBetween(Double minBuget, Double maxBuget, int pageNo, int pageSize, String sortField, String sortDirection);

    public Page<Vacanta> findPaginatedByBugetGreaterThanEqual(Double minBuget, int pageNo, int pageSize, String sortField, String sortDirection);

    public Page<Vacanta> findPaginatedByBugetLessThanEqual(Double maxBuget, int pageNo, int pageSize, String sortField, String sortDirection);

    public List<Vacanta> findBynumeTara(String numeTara);
}