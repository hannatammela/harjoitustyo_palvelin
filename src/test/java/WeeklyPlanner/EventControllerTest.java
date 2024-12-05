package WeeklyPlanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// YKSIKKÖTESTI testAddNewEvent_SaveOk
// Testaa tapahtuman oikean tallennuksen ja
// ja riippuvuuksien toiminnan (käyttäjän ja
// kategorian haku toiminnan)
@ExtendWith(MockitoExtension.class)
    class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private BindingResult bindingResult;

    @Test
    void testAddNewEvent_SaveOk() {
        Event event = new Event();
        event.setName("Testing");
        event.setDayOfWeek("SUNNUNTAI");

        AppUser user = new AppUser();
        user.setUsername("Test User");

        Category category = new Category();
        category.setName("Test Category");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(authentication.getName()).thenReturn("Test User"); // Käyttäjänimi autentikoinnista
        when(appUserRepository.findByUsername("Test User")).thenReturn(user); // Käyttäjä löytyy
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        String testingMethods = eventController.addNewEvent(event, bindingResult, 1L, authentication, null);

        assertEquals("redirect:/events", testingMethods); // Oikea näkymä
        verify(eventRepository).save(event);
    }
}
