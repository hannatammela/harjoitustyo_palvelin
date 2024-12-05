package WeeklyPlanner;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// Mallintaa sovelluksen käyttäjät ja heidän tiedot

// Validoitu, ettei nimi voi olla tyhjä.
@Entity
@Data
@NoArgsConstructor
public class AppUser extends AbstractPersistable<Long> {

    // Validointia.
    @Column(nullable = false)
    @Size(min = 2, max = 20, message = "Käyttäjätunnus tulee olla välillä 2-20 merkkiä.")
    @NotBlank(message = "Nimi ei saa olla tyhjä.")
    private String username;

    @Column(nullable = false)
    @Size(min = 8, message = "Salasanan tulee olla vähintään 8 merkkiä pitkä.")
    @NotBlank(message = "Salasana ei saa olla tyhjä.")
    private String password;

    // Konstruktori kahdella parametrilla jotta käyttäjät
    // voidaan luoda helposti
    public AppUser(String username, String password) {
            this.username = username;
            this.password = password;
    }
}
