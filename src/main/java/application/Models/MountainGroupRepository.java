package application.Models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Mountain group repository.
 */
@Repository
public interface MountainGroupRepository extends CrudRepository<MountainGroup, String> {
}
