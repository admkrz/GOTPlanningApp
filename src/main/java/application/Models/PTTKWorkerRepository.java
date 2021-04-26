package application.Models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Pttk worker repository.
 */
@Repository
public interface PTTKWorkerRepository extends CrudRepository<PTTKWorker, Integer> {
}
