/** Clasa care implementeaza functiile declarate in interfata ActivitateService
 * @author Bădără Denisa
 * @version 23 Decembrie 2024
 */

package com.example.PlanificatorVacanta.service;
import com.example.PlanificatorVacanta.model.Activitate;
import com.example.PlanificatorVacanta.repository.ActivitateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActivitateServiceImpl implements  ActivitateService {

    @Autowired
    private ActivitateRepository activitateRepository;

    public List<Activitate> getActivitatiByVacantaId(Integer vacantaId) {
        return activitateRepository.findByVacanta_Id(vacantaId);
    }


    @Override
    public void saveActivitate(Activitate activitate) {
        activitateRepository.save(activitate);
    }


    @Override
    public Activitate getActivitateById(int id) {
        return activitateRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteActivitateById(int id) {
        activitateRepository.deleteById(id);
    }

    @Override
    public List<Activitate> getAllActivitati() {
        return activitateRepository.findAll();
    }

    @Override
    public double calculateTotalActivityCost(Integer id) {
        List<Activitate> activities = activitateRepository.findByVacanta_Id(id);
        return activities.stream()
                .mapToDouble(Activitate::getCost)
                .sum();
    }

    @Override
    public double calculateTotalActivityCostExcluding(int activitateId, int vacantaId) {
        List<Activitate> activities = activitateRepository.findByVacanta_Id(vacantaId);
        return activities.stream()
                .filter(a -> a.getActivitateID() != activitateId)
                .mapToDouble(Activitate::getCost)
                .sum();
    }

}
