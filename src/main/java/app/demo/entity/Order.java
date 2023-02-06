package app.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  private String name;

  //  @Fetch(FetchMode.SELECT)
  //  @JoinColumn(name = "order_id")
  //  @OneToMany(
  //      fetch = FetchType.LAZY,
  //      cascade = {CascadeType.PERSIST})
  //  private Set<OrderItem> orderItems;

  @PrimaryKeyJoinColumn
  @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
  private OrderStatus orderStatus;

  public void setOrderStatus(OrderStatus orderStatus) {
    if (orderStatus.getOrder() == null || !orderStatus.getOrder().getId().equals(this.id)) {
      orderStatus.setOrder(this);
    }

    this.orderStatus = orderStatus;
  }
}
