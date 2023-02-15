package app.demo.domain.user.id;

import static app.demo.domain.user.id.UserDetailsResponse.of;

import app.demo.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetailsResponse findById(Long id) {

    return of(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException()));
  }
}
