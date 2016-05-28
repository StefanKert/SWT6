package swt6.soccer.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import swt6.soccer.dao.GameRepository;
import swt6.soccer.dao.TeamRepository;
import swt6.soccer.dao.UserRepository;
import swt6.soccer.domain.Game;
import swt6.soccer.domain.Team;
import swt6.soccer.domain.User;

import java.util.List;

/**
 * JPA implementation of the soccer service
 */
@Component
@Transactional
public class SoccerServiceJPA implements SoccerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public User syncUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Team syncTeam(Team team) {
        return teamRepository.saveAndFlush(team);
    }

    @Override
    public Team findTeam(Long id) {
        return teamRepository.findOne(id);
    }

    @Override
    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Game syncGame(Game game) {
        return gameRepository.saveAndFlush(game);
    }
}
