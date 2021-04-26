package application.Models;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The interface Trip repository.
 */
@Repository
public interface TripRepository extends CrudRepository<Trip, Integer> {

    /**
     * Updates trip's status.
     *
     * @param id     the id
     * @param status the status
     */
    @Modifying
    @Transactional
    @Query(value = "update trips t set t.status=:status where t.id=:id")
    void updateStatus( @Param("id") Integer id, @Param("status") TripStatus status);

    /**
     * Finds trips by status equals status.
     *
     * @param status the status
     * @return the iterable
     */
    Iterable<Trip> findTripsByStatusEquals(TripStatus status);
}
