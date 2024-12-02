package WeeklyPlanner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOrderByTimeAsc();

    List<Event> findByUserUsernameOrderByTimeAsc(String username);
}

// Vastaa tietokantakyselyistä
// ja tarjoaa valmiit CRUD-toiminnot (luonti, luku, päivitys, poisto)
