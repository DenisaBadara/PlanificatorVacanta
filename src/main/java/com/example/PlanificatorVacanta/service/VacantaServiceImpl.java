/** Clasa care implementeaza functiile declarate in interfata VacantaService
 * @author Bădără Denisa
 * @version 23 Decembrie 2024
 */

package com.example.PlanificatorVacanta.service;
import com.example.PlanificatorVacanta.model.Vacanta;
import com.example.PlanificatorVacanta.repository.VacantaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VacantaServiceImpl implements VacantaService {
    @Autowired
    private VacantaRepository vacantaRepository;

    @Override
    public List<Vacanta> getAllVacances() {
        List<Vacanta> vacante = vacantaRepository.findAll();
        System.out.println("Vacante găsite: " + vacante);
        return vacante;
        }

    public void testRepository() {
        List<Vacanta> vacante = vacantaRepository.findAll();
        System.out.println("Vacante din repository: " + vacante);
    }


    @Override
    public void saveVacanta(Vacanta vacanta) {
        this.vacantaRepository.save(vacanta);
    }

    @Override
    public Vacanta getVacantabyId(int id){

        Optional<Vacanta> optional = vacantaRepository.findById(id);
        Vacanta vacanta = null;
        if(optional.isPresent()){
            vacanta = optional.get();
        }else{
            throw new RuntimeException("Vacanta nu a fost gasita pentru id-ul::" + id);
        }

        return vacanta;
    }

    @Transactional
    @Override
    public void deleteVacancebyId(int id){
        this.vacantaRepository.deleteById(id);
    }

    public List<Vacanta> getVacanteByBugetLessThanEqual(Double maxBuget) {
        return vacantaRepository.findByBugetTotalLessThanEqual(maxBuget);
    }

    public List<Vacanta> getVacanteByBugetGreaterThanEqual(Double minBuget) {
        return vacantaRepository.findByBugetTotalGreaterThanEqual(minBuget);
    }

    public List<Vacanta> getVacanteByBugetBetween(Double minBuget, Double maxBuget) {
        return vacantaRepository.findByBugetTotalBetween(minBuget, maxBuget);
    }

    @Override
    public Page<Vacanta> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return vacantaRepository.findAll(pageable);
    }

    public Page<Vacanta> findPaginatedByBugetBetween(Double minBuget, Double maxBuget, int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return vacantaRepository.findByBugetTotalBetween(minBuget, maxBuget, pageable);
    }

    public Page<Vacanta> findPaginatedByBugetGreaterThanEqual(Double minBuget, int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return vacantaRepository.findByBugetTotalGreaterThanEqual(minBuget, pageable);
    }

    public Page<Vacanta> findPaginatedByBugetLessThanEqual(Double maxBuget, int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return vacantaRepository.findByBugetTotalLessThanEqual(maxBuget, pageable);
    }

    @Override
    public List<Vacanta> findBynumeTara(String numeTara) {
        return vacantaRepository.findBynumeTara(numeTara);
    }

}
