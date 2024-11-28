package WeeklyPlanner;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Estetään kategorioiden poisto
    @Override
    default void deleteById(Long id) {
        throw new UnsupportedOperationException("Kategorioita ei voida poistaa.");
    }

    @Override
    default void delete(Category entity) {
        throw new UnsupportedOperationException("Kategorioita ei voida poistaa.");
    }
}
