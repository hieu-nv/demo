package app.demo.repository;

import app.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OrderRepository.
 *
 * @author Hieu Nguyen
 */
@SuppressWarnings("unused")
public interface OrderRepository extends JpaRepository<Order, Long> {}
