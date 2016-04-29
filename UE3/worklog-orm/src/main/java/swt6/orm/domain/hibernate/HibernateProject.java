package swt6.orm.domain.hibernate;

import java.util.HashSet;
import java.util.Set;

import swt6.orm.domain.Employee;
import swt6.orm.domain.Module;
import swt6.orm.domain.Project;

public class HibernateProject implements Project {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Set<Employee> members = new HashSet<>();
	private Set<Module> modules = new HashSet<>();

	public Long getId() {
		return id;
	}

	public HibernateProject() {
	}

	public HibernateProject(String name) {
		this.setName(name);
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Employee> getMembers() {
		return members;
	}

	public void setMembers(Set<Employee> members) {
		this.members = members;
	}

	public void addMember(Employee empl) {
		if (empl == null)
			throw new IllegalArgumentException("Employee must not be null.");

		empl.getProjects().add(this);
		this.members.add(empl);
	}

	public void removeMember(Employee empl) {
		if (empl == null)
			throw new IllegalArgumentException("Employee must not be null.");

		empl.getProjects().remove(this);
		this.getMembers().remove(empl);
	}

	public String toString() {
		return name;
	}

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}

	public void addModule(Module module) {
		if (module == null) 
			throw new IllegalArgumentException("Module must not be null.");
		
		if (module.getProject() != null) 
			module.getProject().getModules().remove(module);
		
		module.setProject(this);
		this.getModules().add(module);
	}

	public void removeModule(Module module) {
		if (module == null)
			throw new IllegalArgumentException("Module must not be null.");
		if (module.getProject() != this) 
			throw new IllegalArgumentException("Can't remove project of another module.");
		
		module.setProject(null);
		this.getModules().remove(module);
	}
}
