package app.demo.repository;

import app.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository.
 *
 * @author Hieu Nguyen
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
