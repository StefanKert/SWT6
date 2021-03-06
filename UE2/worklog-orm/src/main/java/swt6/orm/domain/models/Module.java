package swt6.orm.domain.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import swt6.orm.domain.models.LogbookEntry;
import swt6.orm.domain.models.Project;

@Entity
public class Module implements IEntity   {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER, targetEntity = Project.class)
	private Project project;

	@OneToMany(mappedBy = "module", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER, targetEntity = LogbookEntry.class)
	private Set<LogbookEntry> logbookEntries = new HashSet<>();

	public Module() {
	}

	public Module(String name) {
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
	
    public void attachProject(Project project) {
        if(project == null){
            throw new IllegalArgumentException("Guveb project entry must not be null");
        }

        if(this.project != null) {
            this.project.getModules().remove(this);
        }

        this.project = project;
        this.project.getModules().add(this);
    }

	public void addLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Given logbook entry must not be null.");

		if (entry.getModule() != null)
			entry.getModule().getLogbookEntries().remove(entry);

		this.getLogbookEntries().add(entry);
		entry.setModule(this);
	}

	public void removeLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Given logbook entry must not be null.");
		if (entry.getModule() != null && entry.getModule() != this)
			throw new IllegalArgumentException("Can't remove logbook entry of another module.");

		this.getLogbookEntries().remove(entry);
		entry.setModule(null);
	}
}