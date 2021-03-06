package swt6.soccer.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import swt6.soccer.config.AppConfig;

@Entity
public class Game implements Comparable<Game> {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "TEAM_A_ID")
	private Team teamA;

	@ManyToOne
	@JoinColumn(name = "TEAM_B_ID")
	private Team teamB;

	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Bet> bets = new HashSet<>();

	private int goalsA;
	private int goalsB;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MMM-YYYY")
	private Date date;

	public Team getTeamA() {
		return teamA;
	}

	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}

	public Team getTeamB() {
		return teamB;
	}

	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}

	public int getGoalsA() {
		return goalsA;
	}

	public void setGoalsA(int goalsA) {
		this.goalsA = goalsA;
	}

	public int getGoalsB() {
		return goalsB;
	}

	public void setGoalsB(int goalsB) {
		this.goalsB = goalsB;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Set<Bet> getBets() {
		return bets;
	}

	public void setBets(Set<Bet> bets) {
		this.bets = bets;
	}

	public void addBet(Bet bet) {
		if (bet.getGame() != null)
			bet.getGame().bets.remove(bet);

		this.bets.add(bet);
		bet.setGame(this);
	}
	
	public boolean getIsFinished(){
		return this.date.after(AppConfig.referenceDate);
	}

	@Override
	public int compareTo(Game other) {
		return getDate().compareTo(other.getDate());
	}
}