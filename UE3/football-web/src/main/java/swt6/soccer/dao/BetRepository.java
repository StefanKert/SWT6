package swt6.soccer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.soccer.domain.Bet;

public interface BetRepository extends JpaRepository<Bet,Long> {
}
