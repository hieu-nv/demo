package app.demo.repository;

import app.demo.domain.Publisher;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PublisherRepository.
 *
 * @author hieunv
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, UUID> {}
