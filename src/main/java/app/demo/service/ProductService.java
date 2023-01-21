package app.demo.service;

import app.demo.entity.Product;
import app.demo.request.ProductCreateRequest;

public interface ProductService {

  /**
   * Create new product.
   *
   * @param request product data
   * @return Product entity
   */
  Product create(ProductCreateRequest request);
}
