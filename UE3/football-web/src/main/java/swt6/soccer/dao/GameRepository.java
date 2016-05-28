package swt6.soccer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.soccer.domain.Game;

public interface GameRepository extends JpaRepository<Game,Long> {
}
