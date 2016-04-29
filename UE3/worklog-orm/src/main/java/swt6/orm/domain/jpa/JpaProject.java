package swt6.orm.domain.jpa;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import swt6.orm.domain.Employee;
import swt6.orm.domain.Module;
import swt6.orm.domain.Project;

@Entity
public class JpaProject implements Project {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, targetEntity = JpaEmployee.class)
	private Set<Employee> members = new HashSet<>();

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "project", fetch = FetchType.EAGER, targetEntity = JpaModule.class)
	private Set<Module> modules = new HashSet<>();

	public Long getId() {
		return id;
	}

	public JpaProject() {
	}

	public JpaProject(String name) {
		this.name = name;
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
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
	public Set<Employee> getMembers() {
		return members;
	}

	@Override
	public void setMembers(Set<Employee> members) {
		this.members = members;
	}

	@Override
	public void addMember(Employee empl) {
		if (empl == null)
			throw new IllegalArgumentException("Null Employee");
		empl.getProjects().add(this);
		this.members.add(empl);
	}

	public String toString() {
		return name;
	}

	@Override
	public Set<Module> getModules() {
		return modules;
	}

	@Override
	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}

	@Override
	public void addModule(Module entry) {
		if (entry == null) 
			throw new IllegalArgumentException("Module must not be null.");
		
		if (entry.getProject() != null) 
			entry.getProject().getModules().remove(entry);
		
		entry.setProject(this);
		this.getModules().add(entry);
	}

	@Override
	public void removeModule(Module entry) {
		if (entry == null)
			throw new IllegalArgumentException("Module must not be null.");
		if (entry.getProject() != this) 
			throw new IllegalArgumentException("Can't remove project of another module.");
		
		entry.setProject(null);
		this.getModules().remove(entry);
	}
}
