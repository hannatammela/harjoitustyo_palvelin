package WeeklyPlanner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// Mallintaa tapahtumat tietokantaa varten.

// SYÖTTEIDEN VALIDOINTI; käytetty tässä luokassa
// tehtävänannossa vaaditulla tavalla.

// nullable = false on siksi, että se estää vahingossa tapahtuvat
// virheet jos kenttä jätetään tyhjäksi
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event extends AbstractPersistable<Long> {

    @Column(nullable = false)
    @NotBlank(message = "Nimi ei saa olla tyhjä.")
    @Size(min = 1, max = 20, message = "Nimi saa olla max 20 merkkiä.")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Päivä ei saa olla tyhjä.")
    private String dayOfWeek;

    @Column(nullable = false)
    @NotBlank(message = "Aika ei saa olla tyhjä.")
    private String time;

    @ManyToOne
    @JoinColumn(name= "category_id", nullable = false) // yhdistää tämän eventin kategoria tauluun
    private Category category;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false) // yhdistää tämän eventin appuser tauluun
    private AppUser user;
}




