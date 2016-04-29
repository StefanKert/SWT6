package swt6.orm.domain.hibernate;

import java.util.HashSet;
import java.util.Set;

import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Phase;

public class HibernatePhase implements Phase {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
    private Set<LogbookEntry> logbookEntries = new HashSet<>();;

	public HibernatePhase() {
	}

	public HibernatePhase(String name) {
		this.setName(name);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<LogbookEntry> getLogbookEntries() {
		return logbookEntries;
	}

	public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
		this.logbookEntries = logbookEntries;
	}
	
	public void addLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Logbook entry must not be null.");

		if (entry.getPhase() != null)
			entry.getPhase().getLogbookEntries().remove(entry);

		this.getLogbookEntries().add(entry);
		entry.setPhase(this);
	}

	public void removeLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Logbook entry must not be null.");
		if (entry.getPhase() != null && entry.getPhase() != this)
			throw new IllegalArgumentException("Can't remove logbook entry of another phase.");

		this.getLogbookEntries().remove(entry);
		entry.setModule(null);
	}
}
