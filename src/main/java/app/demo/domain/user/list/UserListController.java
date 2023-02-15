package app.demo.domain.user.list;

import app.demo.repository.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController.
 *
 * @author Hieu Nguyen
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserListController {

  private final UserRepository userRepository;

  @GetMapping
  public ResponseEntity<Set<UserResponse>> index() {
    //    var data =
    //        UserResponse.of(
    //            userRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException()));
    var data = userRepository.findAll().stream().map(UserResponse::of).collect(Collectors.toSet());
    return ResponseEntity.ok().body(data);
  }
}
