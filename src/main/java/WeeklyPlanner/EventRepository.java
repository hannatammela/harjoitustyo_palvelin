package WeeklyPlanner;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}

// Vastaa tietokantakyselyistä
// ja tarjoaa valmiit CRUD-toiminnot (luonti, luku, päivitys, poisto)

//findAll() – Hakee kaikki tapahtumat.
//findById(Long id) – Hakee yksittäisen tapahtuman tunnisteen perusteella.
//save(Event event) – Tallentaa uuden tai päivittää olemassa olevan tapahtuman.
//deleteById(Long id) – Poistaa tapahtuman tunnisteen perusteella.
