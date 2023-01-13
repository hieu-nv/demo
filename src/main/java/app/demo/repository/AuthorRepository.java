package app.demo.repository;

import app.demo.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** AuthorRepository. */
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {}
