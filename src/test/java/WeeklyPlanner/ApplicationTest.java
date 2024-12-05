package WeeklyPlanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationTest {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    // INTEGRAATIOTESTI
    // Tarkistaa, että sovellus tallentaa
    // ja hakea tietoa tietokannasta
    @Test
    public void testSaveAndGetEvent() {
        Category category = new Category();
        category.setName("Test Category");
        categoryRepository.save(category);

        AppUser user = new AppUser();
        user.setUsername("Test User");
        user.setPassword("TestPassword123");

        appUserRepository.save(user);

        Event event = new Event();
        event.setName("Test Event");
        event.setDayOfWeek("SUNNUNTAI");
        event.setTime("09:00");
        event.setCategory(category);
        event.setUser(user);

        eventRepository.save(event);

        Event savedEvent = eventRepository.findAllByOrderByTimeAsc().get(0);
        assertEquals("Test Event", savedEvent.getName());
        assertEquals("SUNNUNTAI", savedEvent.getDayOfWeek());
        assertEquals("09:00", savedEvent.getTime());
        assertEquals("Test Category", savedEvent.getCategory().getName());
        assertEquals("Test User", savedEvent.getUser().getUsername());
    }
}
