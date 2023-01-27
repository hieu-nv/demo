package app.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Order.
 *
 * @author Hieu Nguyen
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  @Id
  @GeneratedValue(generator = "Product")
  @TableGenerator(name = "Product", table = "hibernate_sequence")
  private Long id;
}
