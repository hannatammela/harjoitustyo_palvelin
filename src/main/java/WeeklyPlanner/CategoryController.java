package WeeklyPlanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // Tallentaa kategoriat jo valmiiksi kun sovellus käynnistetään
    // Category.javassa yksiparametrinen kontruktori new Categoryn.
    // luomiseen
    @PostConstruct
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

    // Näyttää kaikki kategoriat
    @GetMapping
    public String showAllCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("{id}")
    public String getCategoryById(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategoriaa ei ole."));
        model.addAttribute("category", category);
        return "single-category";
    }
}
