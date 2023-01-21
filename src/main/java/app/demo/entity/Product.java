package app.demo.entity;

import app.demo.request.ProductCreateRequest;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product.
 *
 * @author Hieu Nguyen
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  @Id
  @GeneratedValue(generator = "Product")
  @TableGenerator(name = "Product", table = "hibernate_sequence")
  private Integer id;

  private String name;

  public static Product of(ProductCreateRequest request) {
    return Product.builder().name(request.getName()).build();
  }
}
