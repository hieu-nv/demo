package app.demo.domain.user.id;


public interface UserDetailsService {
  UserDetailsResponse findById(Long id);
}
