package app.demo.service;

import app.demo.entity.Product;
import app.demo.repository.ProductRepository;
import app.demo.request.ProductCreateRequest;
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
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;

  @Override
  @Transactional
  public Product create(ProductCreateRequest request) {
    return productRepository.save(Product.of(request));
  }
}
