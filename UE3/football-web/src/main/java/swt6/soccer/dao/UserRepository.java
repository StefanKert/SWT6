package swt6.soccer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.soccer.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
