package swt6.orm.domain.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Module;
import swt6.orm.domain.Project;

@Entity
public class JpaModule implements Module {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER, targetEntity = JpaProject.class)
	private Project project;

	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = JpaLogbookEntry.class)
	private Set<LogbookEntry> logbookEntries = new HashSet<>();;

	public JpaModule() {
	}

	public JpaModule(String name) {
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
	public Project getProject() {
		return project;
	}

	@Override
	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public Set<LogbookEntry> getLogbookEntries() {
		return logbookEntries;
	}

	@Override
	public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
		this.logbookEntries = logbookEntries;
	}
	
	@Override
	public void addLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Logbook entry must not be null.");

		if (entry.getModule() != null)
			entry.getModule().getLogbookEntries().remove(entry);

		this.getLogbookEntries().add(entry);
		entry.setModule(this);
	}

	@Override
	public void removeLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Logbook entry must not be null.");
		if (entry.getModule() != null && entry.getModule() != this)
			throw new IllegalArgumentException("Can't remove logbook entry of another module.");

		this.getLogbookEntries().remove(entry);
		entry.setModule(null);
	}
}
