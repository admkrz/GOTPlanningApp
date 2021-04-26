package application.Models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Tourist repository.
 */
@Repository
public interface TouristRepository extends CrudRepository<Tourist, Integer> {
}
