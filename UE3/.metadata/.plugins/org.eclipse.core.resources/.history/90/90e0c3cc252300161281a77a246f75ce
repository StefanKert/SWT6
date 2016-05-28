package swt6.soccer.logic;

import swt6.soccer.domain.Game;
import swt6.soccer.domain.Team;
import swt6.soccer.domain.User;

import java.util.List;

/**
 * Soccer service definition
 */
public interface SoccerService {

    /***
     * Creates or updates the user
     * @param user
     * @return
     */
    User syncUser(User user);

    /***
     * Creates or updates the given team
     * @param team
     * @return
     */
    Team syncTeam(Team team);

    /***
     * Finds the team with the given id
     * @param id id of the team
     * @return team object or null
     */
    Team findTeam(Long id);

    /***
     * Fetches all teams
     * @return
     */
    List<Team> findAllTeams();

    /**
     * Creates or updates the given game
     * @return updated game object
     */
    Game syncGame(Game game);
}
