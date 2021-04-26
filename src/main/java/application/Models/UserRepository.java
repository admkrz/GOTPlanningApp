package application.Models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer>
{
    /**
     * Find by email and password.
     *
     * @param email the email
     * @param passw the passw
     * @return the optional
     */
    Optional<User> findByEmailAndPassw(@Param("email") String email, @Param("passw") String passw);
}
