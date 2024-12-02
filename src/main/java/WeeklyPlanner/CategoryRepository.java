package WeeklyPlanner;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

//      Kategorioiden poisto ja muokkaus ei ole mahdollista käyttöliittymän kautta.
//      Ohjelma ajaa tietokantaan tietyt kategoriat käynnistyessään.
//      Tähän ei siksi ole määritelty erillistä kieltoa poistolle tai muokkaukselle.


