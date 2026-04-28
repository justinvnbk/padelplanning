package be.thomasmore.padelplanning.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {
    @GetMapping("/login")
    public String login (Model model, Principal principal) {

        if (principal !=null) return "redirect:/home";

        return "user/login";
    }

    @GetMapping("/logout")
    public String logout () {


        return "user/logout";
    }
}
