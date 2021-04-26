package application.Models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Route repository.
 */
@Repository
public interface RouteRepository extends CrudRepository<Route, Integer> {
}
