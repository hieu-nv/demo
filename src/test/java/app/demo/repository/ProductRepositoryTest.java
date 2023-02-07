package app.demo.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import app.demo.entity.Product;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductRepositoryTest {

  @Autowired private ProductRepository productRepository;

  @Test
  void test() {
    Product product =
        productRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
    assertThat(product.getId(), is(1L));
  }
}
