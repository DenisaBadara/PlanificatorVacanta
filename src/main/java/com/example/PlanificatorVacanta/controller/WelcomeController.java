/** Clasa care implementeaza cereri de tip GET si POST pentru realizarea functionalitatilor paginii
 * de welcome, in care este afisata lista de vacante
 * @author Bădără Denisa
 * @version 27 Decembrie 2024
 */

package com.example.PlanificatorVacanta.controller;
import com.example.PlanificatorVacanta.model.Vacanta;
import com.example.PlanificatorVacanta.service.VacantaServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class WelcomeController {

    private final VacantaServiceImpl vacantaService;

    public WelcomeController(VacantaServiceImpl vacantaService) {
        this.vacantaService = vacantaService;
    }

    // realizarea sortarii in functie de directie si pastrarea paginarii
    @GetMapping("/welcome")
    public String welcomePage(
            @RequestParam(value = "minBuget", required = false) Double minBuget,
            @RequestParam(value = "maxBuget", required = false) Double maxBuget,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageNo,
            @RequestParam(value = "sortField", defaultValue = "numeTara") String sortField,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            Model model) {

        int pageSize = 5;
        Page<Vacanta> page;

        if (minBuget != null && maxBuget != null) {
            page = vacantaService.findPaginatedByBugetBetween(minBuget, maxBuget, pageNo, pageSize, sortField, sortDir);
        } else if (minBuget != null) {
            page = vacantaService.findPaginatedByBugetGreaterThanEqual(minBuget, pageNo, pageSize, sortField, sortDir);
        } else if (maxBuget != null) {
            page = vacantaService.findPaginatedByBugetLessThanEqual(maxBuget, pageNo, pageSize, sortField, sortDir);
        } else {
            page = vacantaService.findPaginated(pageNo, pageSize, sortField, sortDir);
        }

        model.addAttribute("vacante", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return findPaginated(pageNo, minBuget, maxBuget, sortField, sortDir, model);
    }

    // realizarea filtrarii in functie de buget si pastrarea paginarii
    @GetMapping("/page/{pageNo}")
    public String findPaginated(
            @PathVariable(value = "pageNo") int pageNo,
            @RequestParam(value = "minBuget", required = false) Double minBuget,
            @RequestParam(value = "maxBuget", required = false) Double maxBuget,
            @RequestParam(value = "sortField", defaultValue = "numeTara") String sortField,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            Model model) {

        int pageSize = 5;
        Page<Vacanta> page;

        if (minBuget != null && maxBuget != null) {
            page = vacantaService.findPaginatedByBugetBetween(minBuget, maxBuget, pageNo, pageSize, sortField, sortDir);
        } else if (minBuget != null) {
            page = vacantaService.findPaginatedByBugetGreaterThanEqual(minBuget, pageNo, pageSize, sortField, sortDir);
        } else if (maxBuget != null) {
            page = vacantaService.findPaginatedByBugetLessThanEqual(maxBuget, pageNo, pageSize, sortField, sortDir);
        } else {
            page = vacantaService.findPaginated(pageNo, pageSize, sortField, sortDir);
        }

        model.addAttribute("vacante", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("minBuget", minBuget);
        model.addAttribute("maxBuget", maxBuget);

        return "welcome";
    }

    // cautarea vacantei in functie de numele tarii introduse
    @GetMapping("/searchCountry")
    public String searchCountry(@RequestParam("searchCountry") String numeTara, Model model) {
        List<Vacanta> filteredVacations = vacantaService.findBynumeTara(numeTara);

        if (!filteredVacations.isEmpty()) {
            model.addAttribute("vacante", filteredVacations);
        }

        return "welcome";
    }

}