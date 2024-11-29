package WeeklyPlanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

    // Hakee kaikki tapahtumat
    // hakee ne tietokannasta eventRepository.findAll()
    @GetMapping
    public String showAllEvents(Model model) {
        List<Event> events = eventRepository.findAll();

        // Luo eventsByDay, jossa viikonpäivästä tehdään avain
        // Lisää tapahtumat oikeaan viikonpäivään
        Map<String, List<Event>> eventsByDay = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            eventsByDay.put(day.name(), new ArrayList<>());
        }
        for (Event event : events) {
            String dayOfWeek = event.getDayOfWeek();
            eventsByDay.get(dayOfWeek).add(event);
        }

        model.addAttribute("eventsByDay", eventsByDay);

        return "all-events";
    }
    // MIETI VIELÄ, TULEEKO TÄMÄ? XXXXXXXXXXXXXXXXXXX
    // Hakee tapahtuman id:n mukaan
    // Virhekäsittely lisätty, jos tapahtumaa ei löydy
    @GetMapping("/{id}")
    public String getEventById(@PathVariable Long id, Model model) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tapahtumaa ei löydy."));
        model.addAttribute("event", event);
        return "single-event";
    }
    // Luo tyhjän Event-objektin.
    // Lomakkeessa valintalistana
    @GetMapping("/add")
    public String addEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("categories", categoryRepository.findAll());
        return "add-event";
    }

    // Tallentaa uuden tapahtuman
    // Tallennetaan eventRepository.save(event)
    @PostMapping("/add")
    public String addNewEvent(Event event) {
        eventRepository.save(event);
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
        return "edit-event";
    }

    // Päivitetään jo luotu tapahtuma
    @PostMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id, Event editedEvent) {
        Event oldEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tapahtumaa ei löydy"));
        oldEvent.setName(editedEvent.getName());
        oldEvent.setDayOfWeek(editedEvent.getDayOfWeek());
        oldEvent.setTime(editedEvent.getTime());
        oldEvent.setCategory(editedEvent.getCategory());
        eventRepository.save(oldEvent);
        return "redirect:/events";
    }
    // Poistetaan tapahtuma
    @GetMapping("delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
        return "redirect:/events";
    }

}