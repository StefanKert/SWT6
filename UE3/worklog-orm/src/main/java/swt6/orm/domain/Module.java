package swt6.orm.domain;

import java.util.Set;

public interface Module extends EntityBase {

	void setId(Long id);

	String getName();

	void setName(String name);

	Project getProject();

	void setProject(Project project);

	Set<LogbookEntry> getLogbookEntries();

	void setLogbookEntries(Set<LogbookEntry> logbookEntries);

	void addLogbookEntry(LogbookEntry entry);

	void removeLogbookEntry(LogbookEntry entry);

}