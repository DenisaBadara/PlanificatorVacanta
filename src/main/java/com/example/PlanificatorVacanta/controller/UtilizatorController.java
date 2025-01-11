/** Clasa care implementeaza cereri de tip GET si POST pentru realizarea functionalitatilor paginii
 * de login
 * @author Bădără Denisa
 * @version 27 Decembrie 2024
 */

package com.example.PlanificatorVacanta.controller;
import com.example.PlanificatorVacanta.model.Utilizator;
import com.example.PlanificatorVacanta.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UtilizatorController {

    private final UserRepository userRepo;

    public UtilizatorController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // prezentarea paginii de login, odata cu accesarea adresei web http://localhost:8080
    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    // logarea propriu - zisa si verificarile aferente cu baza de date
    @PostMapping("/process-login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            RedirectAttributes redirectAttributes) {
        System.out.println("Cerere POST primita cu username: " + username + " si password: " + password);
        Utilizator user = userRepo.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login reusit pentru utilizatorul: " + username);
            redirectAttributes.addFlashAttribute("success", "Login reusit!");
            return "redirect:/welcome";
        } else {
            System.out.println("Login esuat pentru utilizatorul: " + username);
            redirectAttributes.addFlashAttribute("error", "Credentiale invalide!");
            return "redirect:/";
        }
    }

}
