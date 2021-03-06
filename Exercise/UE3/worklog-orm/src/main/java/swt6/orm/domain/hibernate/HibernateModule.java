package swt6.orm.domain.hibernate;

import java.util.HashSet;
import java.util.Set;

import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Module;
import swt6.orm.domain.Project;

public class HibernateModule implements Module {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Project project;
	private Set<LogbookEntry> logbookEntries = new HashSet<>();;

	public HibernateModule() {
	}

	public HibernateModule(String name) {
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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

		if (entry.getModule() != null)
			entry.getModule().getLogbookEntries().remove(entry);

		this.getLogbookEntries().add(entry);
		entry.setModule(this);
	}

	public void removeLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Logbook entry must not be null.");
		if (entry.getModule() != null && entry.getModule() != this)
			throw new IllegalArgumentException("Can't remove logbook entry of another module.");

		this.getLogbookEntries().remove(entry);
		entry.setModule(null);
	}
}
