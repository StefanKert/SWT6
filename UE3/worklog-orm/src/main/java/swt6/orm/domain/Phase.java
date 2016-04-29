package swt6.orm.domain;

import java.util.Set;

public interface Phase extends EntityBase {

	void setId(Long id);

	String getName();

	void setName(String name);

	void addLogbookEntry(LogbookEntry entry);

	void removeLogbookEntry(LogbookEntry entry);

	Set<LogbookEntry> getLogbookEntries();

	void setLogbookEntries(Set<LogbookEntry> logbookEntries);

}