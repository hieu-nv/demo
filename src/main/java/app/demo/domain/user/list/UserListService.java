package app.demo.domain.user.list;

import java.util.Set;

public interface UserListService {
  Set<UserResponse> findAll();
}
