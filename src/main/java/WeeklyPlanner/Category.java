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

    @OneToMany
    private List<Event> events = new ArrayList<>();
}
