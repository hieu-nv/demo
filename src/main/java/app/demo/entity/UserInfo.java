package app.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * <code>user_info</code>.
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_info")
public class UserInfo {

  /** <code>user_id</code>. */
  @Id
  private Long userId;

  /** <code>first_name</code>. */
  private String firstName;

  /** <code>last_name</code>. */
  private String lastName;

  @MapsId
  @ToString.Exclude
  @Fetch(FetchMode.JOIN)
  @OneToOne(cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.EAGER)
  private User user;
}
