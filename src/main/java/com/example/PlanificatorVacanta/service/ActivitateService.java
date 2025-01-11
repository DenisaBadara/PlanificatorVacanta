/** Clasa de tip interfata folosita pentru a defini functiile din ActivitateServiceImpl,
 * pentru a genera functionalitati legate de tabelul Activitate, precum: salvarea, stergerea,
 * calcularea costului total, gasirea id-ului activitatii
 * @author Bădără Denisa
 * @version 23 Decembrie 2024
 */

package com.example.PlanificatorVacanta.service;
import com.example.PlanificatorVacanta.model.Activitate;
import java.util.List;

public interface ActivitateService {

    public List<Activitate> getActivitatiByVacantaId(Integer vacantaId);

    public List<Activitate> getAllActivitati();

    public void saveActivitate(Activitate activitate);

    public Activitate getActivitateById(int id);

    public void deleteActivitateById(int id);

    public double calculateTotalActivityCost(Integer id);

    public double calculateTotalActivityCostExcluding(int activitateId, int vacantaId);

}
