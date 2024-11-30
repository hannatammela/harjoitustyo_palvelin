package WeeklyPlanner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category extends AbstractPersistable<Long> {

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
