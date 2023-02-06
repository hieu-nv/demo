package app.demo.repository;

import app.demo.entity.Order;
import app.demo.entity.OrderStatus;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/** https://www.jpa-buddy.com/blog/the-ultimate-guide-on-composite-ids-in-jpa-entities/ */
@SpringBootTest
class OrderRepositoryTest {
  @Autowired private OrderRepository orderRepository;

  @Autowired private ProductRepository productRepository;

  @Test
  void test() {
    //    var ois =
    //        Set.of(
    //            OrderItem.builder()
    //                .product(
    //                    productRepository.findById(1L).orElseThrow(() -> new
    // EntityNotFoundException()))
    //                .quantity(2)
    //                .build());
    //    var o = Order.builder().name("ODR-2").build();
    //    ois.stream().forEach((x) -> x.setOrder(o));
    //    o.setOrderItems(ois);

    //    orderRepository.save(o);

    var order = Order.builder().name("ODR-" + UUID.randomUUID()).build();
    order.setOrderStatus(OrderStatus.builder().build());

    orderRepository.save(order);
  }
}
