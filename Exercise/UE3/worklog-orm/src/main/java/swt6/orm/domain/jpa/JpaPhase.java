package swt6.orm.domain.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Phase;

@Entity
public class JpaPhase implements Phase {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@OneToMany(mappedBy = "phase", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = JpaLogbookEntry.class)
	private Set<LogbookEntry> logbookEntries = new HashSet<>();;

	public JpaPhase() {
	}

	public JpaPhase(String name) {
		this.setName(name);
	}

	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void addLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Logbook entry must not be null.");

		if (entry.getPhase() != null)
			entry.getPhase().getLogbookEntries().remove(entry);

		this.getLogbookEntries().add(entry);
		entry.setPhase(this);
	}

	@Override
	public void removeLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Logbook entry must not be null.");
		if (entry.getPhase() != null && entry.getPhase() != this)
			throw new IllegalArgumentException("Can't remove logbook entry of another phase.");

		this.getLogbookEntries().remove(entry);
		entry.setModule(null);
	}

	@Override
	public Set<LogbookEntry> getLogbookEntries() {
		return logbookEntries;
	}

	@Override
	public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
		this.logbookEntries = logbookEntries;
	}
}
