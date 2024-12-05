package WeeklyPlanner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

// Mallintaa kategoriat tietokantaa varten.

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category extends AbstractPersistable<Long> {

    // Validointia.
    @NotBlank(message = "Kategorian nimi ei saa olla tyhjä.")
    @Size(min = 2, max = 20, message = "Kategorian nimen pituus 2-20 merkkiä.")
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Event> events = new ArrayList<>();

    // Yksiparametrinen konstruktori, jotta saadaan
    // kategoriat luotua CategoryControlleriin.
    // Lombok ei luo tätä automaattisesti, niin
    // siksi pitää itse määritellä.
    public Category(String name) {
        this.name = name;
    }
}
