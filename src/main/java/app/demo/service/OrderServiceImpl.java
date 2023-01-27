package app.demo.service;

import app.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * OrderServiceImpl.
 *
 * @author Hieu Nguyen
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  @SuppressWarnings("unused")
  private final OrderRepository orderRepository;
}
