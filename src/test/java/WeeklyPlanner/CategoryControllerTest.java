package WeeklyPlanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// YKSIKKÖTESTI
// Tarkastaa, palauttaako metodi oikean merkkijonon
// Varmistetaan, että controller lisää haetut kategoriat oikein
// Varmistetaan, että findAll-metodia käytetään oikein.
@ExtendWith(MockitoExtension.class)
    class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @Test
    void testShowAllCategories() {
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category("Työ");
        Category category2 = new Category("Koulu");
        Category category3 = new Category("Vapaa-aika");
        Category category4 = new Category("Harrastukset");
        Category category5 = new Category("Muu");

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);

        when(authentication.getName()).thenReturn("Test User");
        when(categoryRepository.findAll()).thenReturn(categories);

        String testingMethod = categoryController.showAllCategories(authentication, model);

        assertEquals("categories", testingMethod);
        verify(model).addAttribute(eq("categories"), eq(categories));
        verify(categoryRepository).findAll();
    }

}
