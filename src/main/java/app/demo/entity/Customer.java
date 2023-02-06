package app.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <code>customer</code>.
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customer")
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {

  /** <code>id</code>. */
  @Id
  @ToString.Include
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** <code>first_name</code> */
  @ToString.Include private String firstName;

  /** <code>last_name</code>. */
  @ToString.Include private String lastName;

  /** <code>account_id</code>. */
  @OneToOne(optional = false, cascade = CascadeType.ALL)
  @JoinColumn(name = "ACCOUNT_ID", unique = true, nullable = false, updatable = false)
  private Account account;
}
