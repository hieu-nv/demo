package app.demo.domain.user.list;

import static java.util.Objects.nonNull;

import app.demo.entity.Passport;
import app.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Builder;
import lombok.Data;

/**
 * UserListResponse.
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
public class UserResponse {
  private Long id;

  private String username;

  private String firstName;

  private String lastName;

  @JsonIncludeProperties({"id", "code"})
  private Passport passport;

  /**
   * UserResponse.
   *
   * @param user User
   * @return UserResponse
   */
  public static UserResponse of(User user) {
    var builder =
        UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .passport(user.getPassport());

    if (nonNull(user.getUserInfo())) {
      var userInfo = user.getUserInfo();
      builder.firstName(userInfo.getFirstName()).lastName(userInfo.getLastName());
    }

    return builder.build();
  }
}
