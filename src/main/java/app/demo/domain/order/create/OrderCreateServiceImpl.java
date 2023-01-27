package app.demo.domain.order.create;

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
public class OrderCreateServiceImpl implements OrderCreateService {
  @SuppressWarnings("unused")
  private final OrderRepository orderRepository;
}
