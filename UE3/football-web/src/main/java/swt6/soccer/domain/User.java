package swt6.soccer.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="\"User\"")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String mail;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Bet> bets = new HashSet<>();
	
	public User() {}
	public User(String name, String mail) {
		this.name = name;
		this.mail = mail;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}	

	public Set<Bet> getBets() {
		return bets;
	}
	public void setBets(Set<Bet> bets) {
		this.bets = bets;
	}

	public void addBet(Bet bet) {		
		if (bet.getUser() != null)
			bet.getUser().bets.remove(bet);

		this.bets.add(bet);
		bet.setUser(this);
	}
	
	public long getPoints() {
		return bets.stream().filter(t -> t.getIsCorrect()).count();
	}

	public Long getId() {
		return id;
	}
}
