/** Clasa care implementeaza cereri de tip GET si POST pentru realizarea functionalitatilor
 * legate de lista de vacanta, precum editarea, adaugarea si stergerea unui obiect din aceasta lista
 * @author Bădără Denisa
 * @version 27 Decembrie 2024
 */

package com.example.PlanificatorVacanta.controller;
import com.example.PlanificatorVacanta.model.Vacanta;
import com.example.PlanificatorVacanta.service.VacantaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
public class VacantaController {

    private final VacantaService vacantaService;

    public VacantaController(VacantaService vacantaService) {
        this.vacantaService = vacantaService;
    }

    // afisarea formularului de adaugare a unei noi vacante
    @GetMapping("/newVacanceForm")
    public String newVacanceForm(Model model) {
        Vacanta vacanta = new Vacanta();
        model.addAttribute("vacanta", vacanta);
        System.out.println("Vacanta trimisa in model: " + vacanta);
        return "vacanta_noua";
    }

    // salvarea unei noi vacante
    @PostMapping("/salveazaVacanta")
    public String salveazaVacanta(@Valid @ModelAttribute("vacanta") Vacanta vacanta, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            if (vacanta.getId() != null) {
                return "update_vacanta";
            } else {
                return "vacanta_noua";
            }
        }
        vacantaService.saveVacanta(vacanta);
        return "redirect:/welcome";
    }

    // afisarea formularului de editare a unei vacante
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model){
        Vacanta vacanta = vacantaService.getVacantabyId(id);
        model.addAttribute("vacanta", vacanta);
        return "update_vacanta";
    }

    // redirectionare catre pagina de confirmare a stergerii
    @GetMapping("/confirmareStergere/{id}")
    public String confirmDelete(@PathVariable Integer id, Model model) {
        Optional<Vacanta> vacantaOpt = Optional.ofNullable(vacantaService.getVacantabyId(id));
        if (vacantaOpt.isPresent()) {
            model.addAttribute("vacantaId", id);
            model.addAttribute("numeTara", vacantaOpt.get().getNumeTara());
            return "confirmare_stergere_vacanta";
        } else {
            return "redirect:/welcome";
        }
    }

    //stergerea unei vacante
    @PostMapping("/deleteVacance/{id}")
    @Transactional
    public String deleteVacance(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        try {
            vacantaService.deleteVacancebyId(id);
            redirectAttributes.addFlashAttribute("success", "Vacanța a fost stearsa cu succes.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Eroare la ștergerea vacantei.");
        }
        return "redirect:/welcome";
    }

}
