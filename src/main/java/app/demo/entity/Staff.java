package app.demo.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Staff.
 *
 * @author Hieu Nguyen
 */
@Data
@Entity(name = "staff")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Staff {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "manager_id")
  private Staff manager;

  @OneToMany(mappedBy = "manager")
  private Set<Staff> subordinateStaffs;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
}
