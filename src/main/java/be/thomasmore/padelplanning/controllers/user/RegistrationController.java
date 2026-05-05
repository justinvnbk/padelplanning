package be.thomasmore.padelplanning.controllers.user;

import jakarta.persistence.Entity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class RegistrationController {

    @GetMapping("/register")
    public String register (Model model, Principal principal) {

        if (principal == null) return "redirect:/";

        return "user/register";
    }
    @PostMapping("/register")
    public String registerPost (Model model, Principal principal) {

        if (principal == null) return "redirect:/";

        return "user/register";
    }
}
