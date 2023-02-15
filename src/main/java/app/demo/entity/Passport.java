package app.demo.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

/**
 * Passport.
 *
 * @author Hieu Nguyen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "passport")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Passport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "code", unique = true)
  private String code;

  /** <code>issued_at</code>. */
  private LocalDateTime issuedAt;

  /** <code>expired_at</code>. */
  private LocalDateTime expiredAt;

  @OneToOne(mappedBy = "passport", fetch = FetchType.EAGER)
  private User user;
}
