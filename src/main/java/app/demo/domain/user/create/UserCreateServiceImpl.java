package app.demo.domain.user.create;

import app.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCreateServiceImpl implements UserCreateService {
  private final UserRepository userRepository;

  /**
   * create(UserCreateRequest).
   *
   * @param request UserCreateRequest
   * @return UserCreateResponse
   */
  @Override
  @Transactional
  public UserCreateResponse create(UserCreateRequest request) {
    return UserCreateResponse.of(userRepository.save(request.toUser()));
  }
}
