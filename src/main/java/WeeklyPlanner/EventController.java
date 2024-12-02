package WeeklyPlanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public String showAllEvents(Authentication authentication, Model model) {
        String username = authentication.getName();
        List<Event> events = eventRepository.findAllByOrderByTimeAsc();

        // Luo eventsByDay listan, jossa kaikki viikonpäivät
        Map<String, List<Event>> eventsByDay = new HashMap<>();
        for (String day : List.of("MAANANTAI", "TIISTAI", "KESKIVIIKKO", "TORSTAI", "PERJANTAI", "LAUANTAI", "SUNNUNTAI")) {
            // Tyhjä lista jokaiselle päivälle
            eventsByDay.put(day, new ArrayList<>());
        }
        // Lisää tietokannasta haetut tapahtumat oikeaan päivään
        for (Event event : events) {
            String dayOfWeek = event.getDayOfWeek() != null ? event.getDayOfWeek().toUpperCase() : "UNKNOWN";
            eventsByDay.getOrDefault(dayOfWeek, new ArrayList<>()).add(event);
        }
        model.addAttribute("eventsByDay", eventsByDay);
        return "all-events";
    }

    // Haetaan id:n mukaan
    @GetMapping("/{id}")
    public String getEventById(@PathVariable Long id, Model model) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tapahtumaa ei löydy."));
        model.addAttribute("event", event);
        return "single-event";
    }

    // Lisää tyhjän lomakkeen tapahtumalle
    @GetMapping("/add")
    public String addEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryRepository.findAll());
        List<String> daysOfWeek = List.of("MAANANTAI", "TIISTAI", "KESKIVIIKKO", "TORSTAI", "PERJANTAI", "LAUANTAI", "SUNNUNTAI");
        model.addAttribute("daysOfWeek", daysOfWeek);
        return "add-event";
    }

    // Tallentaa uuden tapahtuman
    // Tallennetaan eventRepository.save(event)
    @PostMapping("/add")
    public String addNewEvent(@ModelAttribute Event event, @RequestParam Long category, Authentication authentication) {
        String username = authentication.getName();
        AppUser currentUser = new AppUser();
        currentUser.setUsername(username);
        event.setUser(currentUser);
        // Haetaan kategoria ID:n perusteella
        Category selectedCategory = categoryRepository.findById(category)
                .orElseThrow(() -> new RuntimeException("Kategoriaa ei löydy."));
        event.setCategory(selectedCategory); // Asetetaan Event-objektiin oikea Category
        event.setDayOfWeek(event.getDayOfWeek().toUpperCase());
        eventRepository.save(event); // Tallennetaan tapahtuma
        return "redirect:/events";
    }

    // Poistetaan tapahtuma
    @GetMapping("delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
        return "redirect:/events";
    }

    // Lomake muokkaamiselle
    // Muokattava tapahtuma haetaan id:n perusteella
    @GetMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id, Model model) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tapahtumaa ei löydy."));
        model.addAttribute("event", event);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("daysOfWeek", List.of("MAANANTAI", "TIISTAI", "KESKIVIIKKO", "TORSTAI", "PERJANTAI", "LAUANTAI", "SUNNUNTAI"));
        return "edit-event";
    }

    // Päivitetään jo luotu tapahtuma
    @PostMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id, @ModelAttribute Event editedEvent, @RequestParam Long category) {
        Event oldEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tapahtumaa ei löydy"));

        // Päivitetään tapahtuman tiedot
        oldEvent.setName(editedEvent.getName());
        oldEvent.setDayOfWeek(editedEvent.getDayOfWeek());
        oldEvent.setTime(editedEvent.getTime());

        // Hae ja aseta kategoria
        Category selectedCategory = categoryRepository.findById(category)
                .orElseThrow(() -> new RuntimeException("Kategoriaa ei löydy"));
        oldEvent.setCategory(selectedCategory);

        // Tallenna muutokset
        eventRepository.save(oldEvent);
        return "redirect:/events";
    }

    // Haetaan tietyn käyttäjän tapahtumat
    @GetMapping("/events")
    public String getUserEvents(Authentication authentication, Model model) {
        String username = authentication.getName();
        List<Event> events = eventRepository.findAllByOrderByTimeAsc();
        model.addAttribute("events", events);
        return "all-events";
    }
}



