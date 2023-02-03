package app.demo.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Order.
 *
 * @author Hieu Nguyen
 */
@Data
@Entity(name = "`order`")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {
  @Id
  @GeneratedValue(generator = "Product")
  @TableGenerator(name = "Product", table = "hibernate_sequence")
  @EqualsAndHashCode.Include
  private Long id;

  private String name;

  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "order_id")
  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST})
  private Set<OrderItem> orderItems;
}
