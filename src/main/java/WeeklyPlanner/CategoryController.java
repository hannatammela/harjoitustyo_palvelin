package WeeklyPlanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

//Hallitaan kategorioita.

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // Tallentaa kategoriat jo valmiiksi tietokantaan kun sovellus käynnistetään
    // Category.javassa yksiparametrinen kontruktori new Categoryn luomiseen.
    // VALIDOINTI NotBlank.
    @PostConstruct
    @NotBlank(message = "Kategoria ei saa olla tyhjä.")
    public void ordinaryCategories() {
        if (categoryRepository.count() == 0) {
            List<Category> defaultCategories = List.of(
                    new Category("Työ"),
                    new Category("Koulu"),
                    new Category("Harrastukset"),
                    new Category("Vapaa-aika"),
                    new Category("Muu")
            );
            categoryRepository.saveAll(defaultCategories);
        }
    }
    private List<Event> filterEventsByUser(Category category, String username) {
        return category.getEvents().stream()
                .filter(event -> event.getUser().getUsername().equals(username))
                .collect(Collectors.toList());
    }

    // Näyttää kaikki kategoriat
    @GetMapping
    public String showAllCategories(Authentication authentication, Model model) {
        String username = authentication.getName();
        List<Category> categories = categoryRepository.findAll();
        categories.forEach(category -> category.setEvents(filterEventsByUser(category, username)));
        model.addAttribute("categories", categories);
        return "categories";
    }

    // Hakee yksittäisen kategorian
    @GetMapping("{id}")
    public String getCategoryById(@PathVariable Long id, Authentication authentication, Model model) {
        String username = authentication.getName();
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategoriaa ei ole."));
        category.setEvents(filterEventsByUser(category, username));
        model.addAttribute("category", category);
        return "single-category";
    }
}
