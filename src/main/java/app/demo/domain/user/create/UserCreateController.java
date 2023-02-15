package app.demo.domain.user.create;

import static org.springframework.http.ResponseEntity.ok;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserCreateController.
 *
 * @author Hieu Nguyen
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserCreateController {

  private final UserCreateService userCreateService;

  @PostMapping
  public ResponseEntity<UserCreateResponse> create(@RequestBody UserCreateRequest request) {
    var response = userCreateService.create(request);
    return ok().body(response);
  }
}
