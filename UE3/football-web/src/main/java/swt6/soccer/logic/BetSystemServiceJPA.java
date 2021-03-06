package swt6.soccer.logic;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import swt6.soccer.dao.BetRepository;
import swt6.soccer.dao.GameRepository;
import swt6.soccer.dao.UserRepository;
import swt6.soccer.domain.Bet;
import swt6.soccer.domain.Game;
import swt6.soccer.domain.User;

@Component
@Transactional
public class BetSystemServiceJPA implements BetSystemService {
    @Autowired
    private BetRepository betRepository;
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

	@Override
	public List<Game> getAllGamesSortedByDate() {
		List<Game> games = gameRepository.findAll();
		Collections.sort(games);
		return games;
	}
	
	@Override
	public List<User> getAllUsersSortedByPoints() {
		return userRepository.findAll().stream().sorted((user1, user2) -> Long.compare(user2.getPoints(), user1.getPoints())).collect(Collectors.toList());
	}

	@Override
	public Bet saveBetWithGame(Bet bet, long gameId) {
		Game game = gameRepository.getOne(gameId);
	   	game.addBet(bet);
	    return betRepository.saveAndFlush(bet);
	}

	public List<Game> getAllNotFinishedGamesSortedByDate() {
		List<Game> games = getAllGamesSortedByDate();
		return games.stream().filter(game -> { return game.getIsFinished();}).collect(Collectors.toList());
	}
}
