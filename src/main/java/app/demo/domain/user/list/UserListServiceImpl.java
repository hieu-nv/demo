package app.demo.domain.user.list;

import app.demo.repository.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * UserListService.
 *
 * @author Hieu Nguyen
 */
@Service
@RequiredArgsConstructor
public class UserListServiceImpl implements UserListService {
  private final UserRepository userRepository;

  @Override
  public Set<UserResponse> findAll() {
    var data = userRepository.findAll().stream().map(UserResponse::of).collect(Collectors.toSet());
    return data;
  }
}
