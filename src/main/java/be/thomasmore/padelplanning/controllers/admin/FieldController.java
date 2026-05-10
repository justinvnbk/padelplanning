package be.thomasmore.padelplanning.controllers.admin;

import be.thomasmore.padelplanning.model.Field;
import be.thomasmore.padelplanning.repositories.FieldRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class FieldController {
    private final FieldRepository fieldRepository;

    public FieldController(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @GetMapping("/fields")
    public String fields(Model model) {
        List<Field> fields = fieldRepository.getAll();
        model.addAttribute("fields", fields);
        return "admin/fields";
    }

    @GetMapping("/newfield")
    public String newfield(Model model) {
        model.addAttribute("field", new Field());
        return "admin/newfield";
    }

    @PostMapping("/newfield")
    public String postNewfield(Model model, Field field) {
        fieldRepository.save(field);
        return "redirect:/admin/fields";
    }
}
