package app.demo.repository;

import app.demo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserInfoRepository.
 *
 * @author Hieu Nguyen
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {}
