package swt6.orm.domain;

import java.util.Set;

public interface Project extends EntityBase {

	String getName();

	void setName(String name);

	Set<Employee> getMembers();

	void setMembers(Set<Employee> members);

	void addMember(Employee empl);

	Set<Module> getModules();

	void setModules(Set<Module> modules);

	void addModule(Module entry);

	void removeModule(Module entry);

}