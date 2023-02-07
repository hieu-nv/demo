package app.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <code>account</code>.
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "account")
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

  /** <code>id</code>. */
  @Id
  @ToString.Include
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** <code>username</code>. */
  @ToString.Include private String username;

  @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
  private Customer customer;
}
