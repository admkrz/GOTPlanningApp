package application.Models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Mountain area repository.
 */
@Repository
public interface MountainAreaRepository extends CrudRepository<MountainArea, String> {
}
