package app.demo.repository;

import app.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository.
 *
 * @author Hieu Nguyen
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
