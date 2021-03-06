package swt6.orm.domain.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import swt6.orm.domain.models.Employee;
import swt6.orm.domain.models.Module;

@Entity
public class Project implements IEntity {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = false, targetEntity = Employee.class)
	private Employee leader;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER, targetEntity = Employee.class)
	@JoinTable(name = "ProjectEmployee", joinColumns = { @JoinColumn(name = "project_id") }, inverseJoinColumns = { @JoinColumn(name = "employee_id") })
	private Set<Employee> members = new HashSet<>();

	@OneToMany(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, mappedBy = "project", fetch = FetchType.EAGER, targetEntity = Module.class)
	private Set<Module> modules = new HashSet<>();

	public Project() {
	}

	public Project(String name) {
		this.name = name;
	}
	
	public Project(String name, Employee leader) {
		this.name = name;
		this.leader = leader;
	}

	public Long getId() {
		return id;
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

	public Employee getLeader() {
		return leader;
	}

	public void setLeader(Employee leader) {
		this.leader = leader;
	}

	public Set<Employee> getMembers() {
		return members;
	}

	public void setMembers(Set<Employee> members) {
		this.members = members;
	}

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}

	public void addModule(Module entry) {
		if (entry == null)
			throw new IllegalArgumentException("Given module must not be null.");

		if (entry.getProject() != null)
			entry.getProject().getModules().remove(entry);

		entry.setProject(this);
		this.getModules().add(entry);
	}

	public void removeModule(Module entry) {
		if (entry == null)
			throw new IllegalArgumentException("Given module must not be null.");
		if (entry.getProject() != this)
			throw new IllegalArgumentException("Can't remove project of another module.");

		entry.setProject(null);
		this.getModules().remove(entry);
	}

	public void addMember(Employee entry) {
		if (entry == null)
			throw new IllegalArgumentException("Null Employee");
		entry.getProjects().add(this);
		this.members.add(entry);
	}
	

    public void removeMember(Employee entry) {
        if (entry == null) 
            throw new IllegalArgumentException("Given employee entry must not be null");
        this.getMembers().remove(entry);
        entry.getProjects().remove(this);
    }
    

    public void attachLeader(Employee entry) {
        if (entry == null) 
            throw new NullPointerException("Given employee must not be null");
        if (this.leader != null && this.getLeader().getLeadingProjects().contains(this)) 
            this.getLeader().getLeadingProjects().remove(this);

        this.leader = entry;
        this.leader.getLeadingProjects().add(this);
    }

    public void detachLeader() {
        if (this.leader != null && this.getLeader().getLeadingProjects().contains(this)) {
            this.getLeader().getLeadingProjects().remove(this);
        }
        this.leader = null;
    }

	public String toString() {
		return name;
	}

}