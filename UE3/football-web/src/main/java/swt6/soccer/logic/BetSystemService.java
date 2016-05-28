package swt6.soccer.logic;

import java.util.List;

import swt6.soccer.domain.Bet;
import swt6.soccer.domain.Game;
import swt6.soccer.domain.User;

public interface BetSystemService {
    
    List<User> getAllUsersSortedByPoints();

    Bet saveBetWithGame(Bet bet, long gameId);
    
    List<Game> getAllGamesSortedByDate();
    
    List<Game> getAllNotFinishedGamesSortedByDate();
}