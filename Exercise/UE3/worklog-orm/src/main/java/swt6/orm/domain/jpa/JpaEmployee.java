package swt6.orm.domain.jpa;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import swt6.orm.domain.Address;
import swt6.orm.domain.Employee;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Project;

@Entity
@DiscriminatorValue("E")
public class JpaEmployee implements Employee {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private String firstName;
	private String lastName;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, targetEntity=JpaLogbookEntry.class)
	private Set<LogbookEntry> logbookEntries = new HashSet<>();

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = JpaAddress.class)
	@JoinColumn(name = "address_id")
	private Address address;

	@ManyToMany(mappedBy = "members", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = JpaProject.class)
	private Set<Project> projects = new HashSet<>();

	public JpaEmployee() {
	}

	public JpaEmployee(String firstName, String lastName, Date dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public JpaEmployee(String firstName, String lastName, Date dateOfBirth, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public void setAddress(Address address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	@Override
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public Set<LogbookEntry> getLogbookEntries() {
		return logbookEntries;
	}

	@SuppressWarnings("unused")
	private void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
		this.logbookEntries = logbookEntries;
	}

	@Override
	public void addLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Logbook entry must not be null.");

		if (entry.getEmployee() != null)
			entry.getEmployee().getLogbookEntries().remove(entry);

		// Set a birerection link between entry and this employee.
		this.getLogbookEntries().add(entry);
		entry.setEmployee(this);
	}

	@Override
	public void removeLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException("Logbook entry must not be null.");
		if (entry.getEmployee() != null && entry.getEmployee() != this)
			throw new IllegalArgumentException("Can't remove logbook entry of another employee.");

		this.getLogbookEntries().remove(entry);
		entry.setEmployee(null);
	}

	@Override
	public void detach() {
		for (LogbookEntry entry : getLogbookEntries())
			removeLogbookEntry(entry);
	}

	@Override
	public Set<Project> getProjects() {
		return projects;
	}

	@Override
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	@Override
	public void addProject(Project project) {
		if (project == null)
			throw new IllegalArgumentException("procject must not be null.");
		project.getMembers().add(this);
		this.projects.add(project);
	}

	@Override
	public void removeProject(Project project) {
		if (project.getMembers() != null)
			project.getMembers().remove(this);
		this.projects.remove(project);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%d: %s, %s (%4$td.%4$tm.%4$tY)", id, lastName, firstName, dateOfBirth));
		if (address != null) {
			sb.append(" address: " + address.toString());
		}
		return sb.toString();
	}
}