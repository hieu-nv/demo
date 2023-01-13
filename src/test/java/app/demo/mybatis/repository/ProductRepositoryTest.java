package app.demo.mybatis.repository;

import java.util.UUID;
import app.demo.mybatis.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductRepositoryTest {

  @Autowired private ProductRepository productRepository;

  @Test
  void test() {
    String id = UUID.randomUUID().toString();
    Product product = Product.builder().id(id).build();
    productRepository.create(product);
//    productRepository.findById()
  }
}
