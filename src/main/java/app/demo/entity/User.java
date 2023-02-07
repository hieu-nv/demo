package app.demo.entity;

import java.time.LocalDateTime;
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

/** <code>user</code>. */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "`user`")
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

  @ToString.Exclude
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private UserInfo userInfo;
}
