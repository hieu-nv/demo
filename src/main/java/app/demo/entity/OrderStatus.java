package app.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * OrderStatus.
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ORDER_STATUS")
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderStatus {

  @Id @ToString.Include @EqualsAndHashCode.Include private Long orderId;

  @MapsId
  @OneToOne
  @JoinColumn(name = "ORDER_ID")
  private Order order;

  private int status;
}
