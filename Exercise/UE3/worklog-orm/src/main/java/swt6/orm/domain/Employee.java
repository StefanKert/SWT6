package swt6.orm.domain;

import java.util.Date;
import java.util.Set;

public interface Employee extends EntityBase {

	Address getAddress();

	void setAddress(Address address);

	String getFirstName();

	Date getDateOfBirth();

	void setDateOfBirth(Date dateOfBirth);

	void setFirstName(String firstName);

	String getLastName();

	void setLastName(String lastName);

	Set<LogbookEntry> getLogbookEntries();

	void addLogbookEntry(LogbookEntry entry);

	void removeLogbookEntry(LogbookEntry entry);

	void detach();

	Set<Project> getProjects();

	void setProjects(Set<Project> projects);

	void addProject(Project project);

	void removeProject(Project project);

}