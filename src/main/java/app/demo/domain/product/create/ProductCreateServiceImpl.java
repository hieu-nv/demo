package app.demo.domain.product.create;

import app.demo.entity.Product;
import app.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProductRepository.
 *
 * @author Hieu Nguyen
 */
@Service
@RequiredArgsConstructor
public class ProductCreateServiceImpl implements ProductCreateService {
  private final ProductRepository productRepository;

  @Override
  @Transactional
  public Product create(ProductCreateRequest request) {
    return productRepository.save(Product.of(request));
  }
}
