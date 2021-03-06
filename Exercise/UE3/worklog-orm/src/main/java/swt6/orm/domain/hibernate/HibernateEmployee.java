package swt6.orm.domain.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import swt6.orm.domain.Address;
import swt6.orm.domain.Employee;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Project;

public class HibernateEmployee implements Employee {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private Address address;
	private Set<LogbookEntry> logbookEntries = new HashSet<>();
	private Set<Project> projects = new HashSet<>();

	public HibernateEmployee() {
	}

	public HibernateEmployee(String firstName, String lastName, Date dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public HibernateEmployee(String firstName, String lastName, Date dateOfBirth,
			Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

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

	public String getFirstName() {
		return firstName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

	public Set<LogbookEntry> getLogbookEntries() {
		return logbookEntries;
	}

	@SuppressWarnings("unused")
	private void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
		this.logbookEntries = logbookEntries;
	}

	public void addLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException(
					"Logbook entry must not be null.");

		if (entry.getEmployee() != null)
			entry.getEmployee().getLogbookEntries().remove(entry);

		this.getLogbookEntries().add(entry);
		entry.setEmployee(this);
	}

	public void removeLogbookEntry(LogbookEntry entry) {
		if (entry == null)
			throw new IllegalArgumentException(
					"Logbook entry must not be null.");
		if (entry.getEmployee() != null && entry.getEmployee() != this)
			throw new IllegalArgumentException(
					"Can't remove logbook entry of another employee.");

		this.getLogbookEntries().remove(entry);
		entry.setEmployee(null);
	}

	public void detach() {
		for (LogbookEntry entry : getLogbookEntries())
			removeLogbookEntry(entry);
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public void addProject(Project project) {
		if (project == null)
			throw new IllegalArgumentException("procject must not be null.");
		project.getMembers().add(this);
		this.projects.add(project);
	}

	public void removeProject(Project project) {
		if (project.getMembers() != null)
			project.getMembers().remove(this);
		this.projects.remove(project);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%d: %s, %s (%4$td.%4$tm.%4$tY)", id, lastName,
				firstName, dateOfBirth));
		if (address != null) {
			sb.append(" address: " + address.toString());
		}
		return sb.toString();
	}
}