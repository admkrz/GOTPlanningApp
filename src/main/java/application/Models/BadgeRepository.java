package application.Models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Badge repository.
 */
@Repository
public interface BadgeRepository extends CrudRepository<Badge, Integer> {
}
