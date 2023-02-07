package app.demo.domain.product.create;

import app.demo.entity.Product;

public interface ProductCreateService {

  /**
   * Create new product.
   *
   * @param request product data
   * @return Product entity
   */
  Product create(ProductCreateRequest request);
}
