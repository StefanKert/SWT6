package swt6.soccer.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Bet {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private int tippedGoalsA;
	private int tippedGoalsB;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private User user;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Game game;
	
	public Bet() { }
	public Bet(int tippedGoalsA, int tippedGoalsB) {
		this.tippedGoalsA = tippedGoalsA;
		this.tippedGoalsB = tippedGoalsB;
	}
	
	public int getTippedGoalsA() {
		return tippedGoalsA;
	}
	public void setTippedGoalsA(int tippedGoalsA) {
		this.tippedGoalsA = tippedGoalsA;
	}
	
	public int getTippedGoalsB() {
		return tippedGoalsB;
	}
	public void setTippedGoalsB(int tippedGoalsB) {
		this.tippedGoalsB = tippedGoalsB;
	}	

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	public boolean getIsCorrect() {
		return tippedGoalsA == game.getGoalsA() && tippedGoalsB == game.getGoalsB();
	}
	public Long getId() {
		return id;
	}
}