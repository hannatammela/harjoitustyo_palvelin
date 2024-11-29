package WeeklyPlanner;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
//      Miten kategorioiden poisto ja muokkaus?
//      Kannattaako jättää lomakkeesta vain poista ja muokkaa
//      toiminnot pois?
//
//      //Vaihtoehtoisia tapoja lisätä CategoryRepositoryyn
//      // nämä:
//
//    // Estetään kategorioiden poisto
//    @Override
//    default void deleteById(Long id) {
//        throw new UnsupportedOperationException("Kategorioita ei voida poistaa.");
//    }
//
//    @Override
//    default void delete(Category entity) {
//        throw new UnsupportedOperationException("Kategorioita ei voida poistaa.");
//    }
//
//    // Estetään kategorioiden kaikenlainen muokkaus.
//    @Override
//    default <S extends Category> S save(S entity) {
//            throw new UnsupportedOperationException("Kategorioita ei voida muokata.");
//        }
//    }