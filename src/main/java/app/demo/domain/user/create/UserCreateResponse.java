package app.demo.domain.user.create;

import app.demo.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserCreateResponse.
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserCreateResponse {
  @EqualsAndHashCode.Include private Long id;

  public static UserCreateResponse of(User user) {
    return UserCreateResponse.builder().id(user.getId()).build();
  }
}
