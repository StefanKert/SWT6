package swt6.soccer.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import swt6.soccer.dao.GameRepository;
import swt6.soccer.dao.TeamRepository;
import swt6.soccer.dao.UserRepository;
import swt6.soccer.domain.Game;
import swt6.soccer.domain.Team;
import swt6.soccer.domain.User;
import swt6.util.DateUtil;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GameRepository gameRepository;
    
    @Override
    public void run(String... args) throws Exception {
        addTeams();
        addUsers();
        addGames();
    }

    private void addUsers() {
    	userRepository.saveAndFlush(new User("Stefan Kert","stefan.kert@inode.at"));
    	userRepository.saveAndFlush(new User("Tester","test@test.at"));
    	userRepository.saveAndFlush(new User("Tester2","test2@test.at"));
    	userRepository.saveAndFlush(new User("Tester3","test3@test.at"));
    }

    private void addTeams() {
    	//Group A
        teamRepository.saveAndFlush(new Team("Frankreich")); // id 1
        teamRepository.saveAndFlush(new Team("Rumänien")); // id 2
        teamRepository.saveAndFlush(new Team("Albanien")); // id 3
        teamRepository.saveAndFlush(new Team("Schweiz")); // id 4
        
    	//Group B        
        teamRepository.saveAndFlush(new Team("England")); // id 5
        teamRepository.saveAndFlush(new Team("Russland")); // id 6
        teamRepository.saveAndFlush(new Team("Wales")); // id 7
        teamRepository.saveAndFlush(new Team("Slowakei")); // id 8
        
    	//Group C        
        teamRepository.saveAndFlush(new Team("Deutschland")); // id 9
        teamRepository.saveAndFlush(new Team("Ukraine")); // id 10
        teamRepository.saveAndFlush(new Team("Polen")); // id 11
        teamRepository.saveAndFlush(new Team("Nordirland")); // id 12
        
    	//Group D        
        teamRepository.saveAndFlush(new Team("Spanien")); // id 13
        teamRepository.saveAndFlush(new Team("Tschechien")); // id 14
        teamRepository.saveAndFlush(new Team("Türkei")); // id 15
        teamRepository.saveAndFlush(new Team("Kroatien")); // id 16
        
    	//Group E        
        teamRepository.saveAndFlush(new Team("Belgien")); // id 17
        teamRepository.saveAndFlush(new Team("Italien")); // id 18
        teamRepository.saveAndFlush(new Team("Irland")); // id 19
        teamRepository.saveAndFlush(new Team("Schweden")); // id 20
        
    	//Group F        
        teamRepository.saveAndFlush(new Team("Portugal")); // id 21
        teamRepository.saveAndFlush(new Team("Island")); // id 22
        teamRepository.saveAndFlush(new Team("Österreich")); // id 23
        teamRepository.saveAndFlush(new Team("Ungarn")); // id 24
    }
    
    private void addGames() {
    	//Group A
    	addGameForIds(DateUtil.getDate(2016, 6, 10), 1, 2);
    	addGameForIds(DateUtil.getDate(2016, 6, 11), 3, 4);
    	addGameForIds(DateUtil.getDate(2016, 6, 15), 2, 4);
    	addGameForIds(DateUtil.getDate(2016, 6, 15), 1, 3);
    	addGameForIds(DateUtil.getDate(2016, 6, 19), 2, 3);
    	addGameForIds(DateUtil.getDate(2016, 6, 19), 4, 1);
    	
    	//Group B
    	addGameForIds(DateUtil.getDate(2016, 6, 11), 7, 8);
    	addGameForIds(DateUtil.getDate(2016, 6, 11), 5, 6);
    	addGameForIds(DateUtil.getDate(2016, 6, 15), 6, 8);
    	addGameForIds(DateUtil.getDate(2016, 6, 16), 5, 7);
    	addGameForIds(DateUtil.getDate(2016, 6, 20), 8, 5);
    	addGameForIds(DateUtil.getDate(2016, 6, 20), 6, 7);
    	
      	//Group C
    	addGameForIds(DateUtil.getDate(2016, 6, 12), 11, 12);
    	addGameForIds(DateUtil.getDate(2016, 6, 12), 9, 10);
    	addGameForIds(DateUtil.getDate(2016, 6, 16), 10, 12);
    	addGameForIds(DateUtil.getDate(2016, 6, 16), 9, 11);
    	addGameForIds(DateUtil.getDate(2016, 6, 21), 10, 11);
    	addGameForIds(DateUtil.getDate(2016, 6, 21), 12, 9);
    	
      	//Group D
    	addGameForIds(DateUtil.getDate(2016, 6, 12), 15, 16);
    	addGameForIds(DateUtil.getDate(2016, 6, 13), 13, 14);
    	addGameForIds(DateUtil.getDate(2016, 6, 17), 14, 16);
    	addGameForIds(DateUtil.getDate(2016, 6, 17), 13, 15);
    	addGameForIds(DateUtil.getDate(2016, 6, 21), 14, 15);
    	addGameForIds(DateUtil.getDate(2016, 6, 21), 16, 13);
    	
      	//Group E
    	addGameForIds(DateUtil.getDate(2016, 6, 13), 19, 20);
    	addGameForIds(DateUtil.getDate(2016, 6, 13), 17, 18);
    	addGameForIds(DateUtil.getDate(2016, 6, 17), 18, 20);
    	addGameForIds(DateUtil.getDate(2016, 6, 18), 17, 19);
    	addGameForIds(DateUtil.getDate(2016, 6, 22), 18, 19);
    	addGameForIds(DateUtil.getDate(2016, 6, 22), 20, 17);
    	
      	//Group F
    	addGameForIds(DateUtil.getDate(2016, 6, 14), 23, 24);
    	addGameForIds(DateUtil.getDate(2016, 6, 14), 21, 22);
    	addGameForIds(DateUtil.getDate(2016, 6, 18), 22, 24);
    	addGameForIds(DateUtil.getDate(2016, 6, 18), 21, 23);
    	addGameForIds(DateUtil.getDate(2016, 6, 22), 22, 23);
    	addGameForIds(DateUtil.getDate(2016, 6, 22), 24, 21);
    }
    
    private void addGameForIds(Date date, int idTeamA, int idTeamB){
    	Game game = new Game();
    	game.setDate(date);
    	game.setTeamA(teamRepository.getOne((long) idTeamA));
    	game.setTeamB(teamRepository.getOne((long) idTeamB));
        gameRepository.saveAndFlush(game);
    }
}