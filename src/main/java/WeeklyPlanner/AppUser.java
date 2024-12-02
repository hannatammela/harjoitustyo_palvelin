package WeeklyPlanner;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class AppUser extends AbstractPersistable<Long> {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // Konstruktori kahdella parametrilla jotta käyttäjät
    // voidaan luoda helposti
    public AppUser(String username, String password) {
            this.username = username;
            this.password = password;
    }
}
