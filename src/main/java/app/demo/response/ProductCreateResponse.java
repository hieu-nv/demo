package app.demo.response;

import app.demo.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProductCreateResponse.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateResponse {
  private Long id;

  private String name;

  public static ProductCreateResponse of(Product product) {
    return ProductCreateResponse.builder().id(product.getId()).name(product.getName()).build();
  }
}
