package swt6.orm.domain.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import swt6.orm.domain.models.Address;
import swt6.orm.domain.models.LogbookEntry;
import swt6.orm.domain.models.Project;

@Entity
@DiscriminatorValue("E")
public class Employee implements IEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private String firstName;
	private String lastName;
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@ManyToMany(mappedBy = "members", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, targetEntity = Project.class)
	private Set<Project> projects = new HashSet<>();

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, targetEntity = LogbookEntry.class)
	private Set<LogbookEntry> logbookEntries = new HashSet<>();

	@OneToMany(mappedBy = "leader", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, targetEntity = Project.class)
	private Set<Project> leadingProjects = new HashSet<>();

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Address.class)
	@JoinColumn(name = "address_id")
	private Address address;

	public Employee() {}

	public Employee(String firstName, String lastName, Date dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public Employee(String firstName, String lastName, Date dateOfBirth, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}
	

	public Long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
	
	public Set<LogbookEntry> getLogbookEntries() {
		return logbookEntries;
	}

	public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
		this.logbookEntries = logbookEntries;
	}
	
    public Set<Project> getLeadingProjects() {
        return leadingProjects;
    }

    public void setLeadingProjects(Set<Project> leadingProjects) {
        this.leadingProjects = leadingProjects;
    }

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	
	
	public void addLogbookEntry(LogbookEntry entry) {
		if (entry.getEmployee() != null)
			entry.getEmployee().getLogbookEntries().remove(entry);

		this.getLogbookEntries().add(entry);
		entry.setEmployee(this);
	}

	public void removeLogbookEntry(LogbookEntry entry) {
		this.getLogbookEntries().remove(entry);
		entry.setEmployee(null);
	}

	public void addProject(Project project) {
		if (project == null)
			throw new IllegalArgumentException("The given project must not be null.");
		project.getMembers().add(this);
		this.projects.add(project);
	}

	public void removeProject(Project project) {
		if (project == null)
			throw new IllegalArgumentException("The given project must not be null.");
		
		this.projects.remove(project);
		project.getMembers().remove(this);
	}


    public void addLeadingProject(Project project) {
        if (project == null) 
        	throw new IllegalArgumentException("The given project must not be null.");
        
        if (project.getLeader() != null) {
            project.getLeader().getLeadingProjects().remove(project);
        }

        this.getLeadingProjects().add(project);
        project.setLeader(this);
    }

    public void removeLeadingProject(Project project) {
        if (project == null) 
        	throw new IllegalArgumentException("The given project must not be null.");

        this.getLeadingProjects().remove(project);
        project.setLeader(null);
    }
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(id + ": " + lastName + ", " +  firstName + ", " + dateOfBirth);
		if (address != null) {
			sb.append(" address: " + address.toString());
		}
		return sb.toString();
	}
}