package app.demo.domain.user.create;

import app.demo.entity.User;
import app.demo.entity.UserInfo;
import lombok.Builder;
import lombok.Data;

/**
 * UserCreateRequest.
 *
 * @author Hieu Nguyen
 */
@Data
@Builder
public class UserCreateRequest {
  private String username;

  private String firstName;

  private String lastName;

  /**
   * toUser().
   *
   * @return User
   */
  public User toUser() {
    var userInfo = UserInfo.builder().firstName(this.firstName).lastName(this.lastName).build();
    var user = User.builder().username(this.username).userInfo(userInfo).build();
//    userInfo.setUser(user);
    return user;
  }
}
