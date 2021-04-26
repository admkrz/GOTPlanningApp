package application.Models;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface Trip segment repository.
 */
@Repository
public interface TripSegmentRepository extends CrudRepository<TripSegment, Integer> {

    /**
     * Update points.
     *
     * @param id     the id
     * @param points the points
     */
    @Modifying
    @Transactional
    @Query(value = "update trip_segments t set t.points=:points where t.id=:id")
    void updatePoints(@Param("id") Integer id, @Param("points") Integer points);
}
