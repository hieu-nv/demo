package app.demo;

import app.demo.entity.User;
import app.demo.entity.UserInfo;
import app.demo.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Main.
 *
 * @author Hieu Nguyen
 */
@Component
@RequiredArgsConstructor
public class Main implements CommandLineRunner {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    var uuid = UUID.randomUUID();
    var userInfo = UserInfo.builder().firstName("Hieu-" + uuid).lastName("Nguyen-" + uuid).build();
    var user = User.builder().username("hieunv-" + UUID.randomUUID()).userInfo(userInfo).build();
    userInfo.setUser(user);
    userRepository.save(user);
  }
}
