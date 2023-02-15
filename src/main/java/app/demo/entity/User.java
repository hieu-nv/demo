package app.demo.entity;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * <code>user</code>.
 *
 * <p>https://thorben-janssen.com/hibernate-performance-tuning/
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

  /** <code>id</code>. */
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** <code>username</code>. */
  private String username;

  /** <code>created_at</code>. */
  private LocalDateTime createdAt;

  /** <code>created_by</code>. */
  private Long createdBy;

  /** <code>updated_at</code>. */
  private LocalDateTime updatedAt;

  /** <code>updated_by</code>. */
  private Long updatedBy;

  /** <code>deleted_at</code>. */
  private LocalDateTime deletedAt;

  /** <code>deleted_by</code>. */
  private Long deletedBy;

  /** <code>user_info.user_id</code> */
  @ToString.Exclude
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private UserInfo userInfo;
  /** <code>passport_id</code>. */
  @Fetch(FetchMode.JOIN)
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "passport_id", unique = true)
  private Passport passport;
}
