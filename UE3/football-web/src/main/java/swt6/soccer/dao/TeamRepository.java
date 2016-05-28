package swt6.soccer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.soccer.domain.Team;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
