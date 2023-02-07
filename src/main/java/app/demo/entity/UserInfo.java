package app.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

/** <code>user_info</code>. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "`user_info`")
public class UserInfo {

  /** <code>user_id</code>. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  /** <code>first_name</code>. */
  private String firstName;

  /** <code>last_name</code>. */
  private String lastName;

  @MapsId
  @ToString.Exclude
  @PrimaryKeyJoinColumn
  @OneToOne(cascade = CascadeType.ALL, optional = false)
  private User user;
}
