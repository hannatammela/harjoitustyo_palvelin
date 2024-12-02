package WeeklyPlanner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event extends AbstractPersistable<Long> {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String dayOfWeek;

    @Column(nullable = false)
    private String time;

    @ManyToOne
    @JoinColumn(name= "category_id", nullable = false) // yhdistää tämän eventin kategoria tauluun
    private Category category;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private AppUser user;
}

// nullable = false on siksi, että se estää vahingossa tapahtuvat
// virheet jos kenttä jätetään tyhjäksi..


