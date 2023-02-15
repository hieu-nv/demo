package app.demo.domain.user.id;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserDetailsController.
 *
 * @author Hieu Nguyen
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{id}")
public class UserDetailsController {
  private final UserDetailsService userDetailsService;

  @GetMapping
  public ResponseEntity<UserDetailsResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok().body(userDetailsService.findById(id));
  }
}
