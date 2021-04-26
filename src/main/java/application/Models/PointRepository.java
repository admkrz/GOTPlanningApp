package application.Models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Point repository.
 */
@Repository
public interface PointRepository extends CrudRepository<Point, String> {
}
