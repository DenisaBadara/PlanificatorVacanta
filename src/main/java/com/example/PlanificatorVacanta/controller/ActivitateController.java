/** Clasa care implementeaza cereri de tip GET si POST pentru realizarea functionalitatilor
 * legate de lista de activitati, precum editarea, adaugarea si stergerea unui obiect din aceasta lista
 * @author Bădără Denisa
 * @version 27 Decembrie 2024
 */

package com.example.PlanificatorVacanta.controller;
import com.example.PlanificatorVacanta.model.Activitate;
import com.example.PlanificatorVacanta.model.Vacanta;
import com.example.PlanificatorVacanta.repository.ActivitateRepository;
import com.example.PlanificatorVacanta.service.ActivitateServiceImpl;
import com.example.PlanificatorVacanta.service.VacantaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class ActivitateController {

    private final ActivitateServiceImpl activitateService;

    private final VacantaService vacantaService;

    private final ActivitateRepository activitateRepository;

    public ActivitateController(VacantaService vacantaService, ActivitateServiceImpl activitateService, ActivitateRepository activitateRepository) {
        this.vacantaService = vacantaService;
        this.activitateService = activitateService;
        this.activitateRepository = activitateRepository;
    }

    // afisarea activitatilor corespunzatoare vacantelor si a formularului de adaugare
    @GetMapping("/vacanta/{id}/activitati")
    public String activitatiPerVacanta(@PathVariable("id") Integer vacantaId,
                                       @RequestParam(value = "showForm", required = false) Boolean showForm,
                                       Model model) {
        Vacanta vacanta = vacantaService.getVacantabyId(vacantaId);
        if (vacanta == null) {
            model.addAttribute("error", "Vacanta nu a fost gasita.");
        }

        List<Activitate> activitati = activitateService.getActivitatiByVacantaId(vacantaId);
        model.addAttribute("vacanta", vacanta);
        model.addAttribute("activitati", activitati);

        double totalActivityCost = activitateService.calculateTotalActivityCost(vacantaId);
        model.addAttribute("totalActivityCost", totalActivityCost);
        assert vacanta != null;
        model.addAttribute("budgetExceeded", totalActivityCost > vacanta.getBugetTotal());

        Activitate activitate = new Activitate();
        activitate.setVacanta(vacanta);
        model.addAttribute("activitate", activitate);

        model.addAttribute("showForm", showForm != null ? showForm : false);

        return "activitati";
    }

    // afisarea paginii de editare a unei activitati
    @GetMapping("/activitate/edit/{id}")
    public String showFormForUpdate(@PathVariable("id") int activitateId, Model model) {
        Activitate activitate = activitateService.getActivitateById(activitateId);
        if (activitate == null) {
            return "redirect:/error";
        }

        Vacanta vacanta = activitate.getVacanta();
        if (vacanta == null) {
            return "redirect:/error";
        }

        double totalActivityCostExcludingCurrent = activitateService.calculateTotalActivityCostExcluding(activitateId, vacanta.getId());

        model.addAttribute("activitate", activitate);
        model.addAttribute("vacanta", vacanta);
        model.addAttribute("totalActivityCostExcludingCurrent", totalActivityCostExcludingCurrent);
        model.addAttribute("bugetTotal", vacanta.getBugetTotal());

        return "editare_activitate";
    }

    // editarea unei activitati
    @PostMapping("/activitate/update")
    public String updateActivitate(
            @Valid @ModelAttribute("activitate") Activitate activitate,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("activitate", activitate);
            model.addAttribute("error", "Exista erori in datele introduse. Va rugam sa verificati campurile.");
            return "editare_activitate";
        }

        if (activitate.getVacanta() == null || activitate.getVacanta().getId() == null) {
            model.addAttribute("activitate", activitate);
            model.addAttribute("error", "Vacanta asociata activitatii nu poate fi null.");
            return "editare_activitate";
        }

        Vacanta vacanta = vacantaService.getVacantabyId(activitate.getVacanta().getId());
        activitate.setVacanta(vacanta);

        activitateService.saveActivitate(activitate);

        redirectAttributes.addFlashAttribute("success", "Activitatea a fost actualizata cu succes.");
        return "redirect:/vacanta/" + vacanta.getId() + "/activitati";
    }

    // adaugarea unei activitati
    @PostMapping("/vacanta/{id}/activitati/adauga")
    public String adaugaActivitate(@PathVariable("id") Integer vacantaId,
                                   @Valid @ModelAttribute("activitate") Activitate activitate,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        Vacanta vacanta = vacantaService.getVacantabyId(vacantaId);
        if (vacanta == null) {
            redirectAttributes.addFlashAttribute("error", "Vacanța nu a fost gasita.");
            return "redirect:/welcome";
        }

        double totalActivityCost = activitateService.calculateTotalActivityCost(vacantaId);

        if (totalActivityCost + activitate.getCost() > vacanta.getBugetTotal()) {
            bindingResult.rejectValue("cost", "error.activitate", "Costul total al activitatilor depaseste bugetul vacanței!");
        }

        if (bindingResult.hasErrors()) {
            List<Activitate> activitati = activitateService.getActivitatiByVacantaId(vacantaId);
            model.addAttribute("vacanta", vacanta);
            model.addAttribute("activitati", activitati);
            model.addAttribute("totalActivityCost", totalActivityCost);
            model.addAttribute("budgetExceeded", (totalActivityCost + activitate.getCost()) > vacanta.getBugetTotal());

            model.addAttribute("showForm", true);
            return "activitati";
        }

        activitate.setVacanta(vacanta);
        activitateService.saveActivitate(activitate);

        redirectAttributes.addFlashAttribute("success", "Activitatea a fost adaugată cu succes.");
        return "redirect:/vacanta/" + vacantaId + "/activitati";
    }

    // afisarea paginii de confirmare pentru stergerea unei activitati
    @GetMapping("/activitate/confirmareStergereActivitate/{activitateId}")
    public String confirmDeleteActivitate(@PathVariable("activitateId") Integer activitateId, Model model) {
        Activitate activitate = activitateService.getActivitateById(activitateId);
        if (activitate == null) {
            return "redirect:/welcome";
        }
        model.addAttribute("activitateId", activitateId);
        model.addAttribute("vacantaId", activitate.getVacanta().getId());
        return "confirmare_stergere_activitate";
    }

    // stergerea unei activitati
    @PostMapping("/vacanta/{vacantaId}/activitati/delete/{activitateId}")
    public String deleteActivitate(
            @PathVariable("vacantaId") Integer vacantaId,
            @PathVariable("activitateId") Integer activitateID,
            RedirectAttributes redirectAttributes) {
        try {
            activitateService.deleteActivitateById(activitateID);
            redirectAttributes.addFlashAttribute("success", "Activitatea a fost stearsa cu succes.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Eroare la stergerea activitatii.");
        }
        return "redirect:/vacanta/" + vacantaId + "/activitati";
    }

    // afisarea daca suma costurilor depaseste sau nu bugetul
    @GetMapping("/vacanta/{id}/activitati/buget")
    public String getActivitiesForVacation(@PathVariable Integer id, Model model) {
        Vacanta vacanta = vacantaService.getVacantabyId(id);

        double totalActivityCost = activitateService.calculateTotalActivityCost(id);

        model.addAttribute("vacanta", vacanta);
        model.addAttribute("totalActivityCost", totalActivityCost);
        model.addAttribute("activities", activitateRepository.findByVacanta_Id(id));
        model.addAttribute("budgetExceeded", totalActivityCost > vacanta.getBugetTotal());

        System.out.println("Vacanta: " + vacanta);
        System.out.println("Activitati: " + activitateRepository.findByVacanta_Id(id));
        System.out.println("Cost total activitati: " + totalActivityCost);

        return "activitati";
    }
}
