package swt6.orm.domain;

import java.util.List;

import swt6.orm.data.IDataProvider;
import swt6.orm.domain.models.Address;
import swt6.orm.domain.models.Employee;
import swt6.orm.domain.models.IEntity;
import swt6.orm.domain.models.LogbookEntry;
import swt6.orm.domain.models.Module;
import swt6.orm.domain.models.Phase;
import swt6.orm.domain.models.Project;


public abstract class WorkLogManager  {
	protected abstract <T extends IEntity> IDataProvider<T> getDataProviderForType();

    public Long createEmployee(Employee employee) {
    	return  this.<Employee>getDataProviderForType().create(employee);
    }
    
    public Employee updateEmployee(Employee employee) {
    	return  this.<Employee>getDataProviderForType().update(employee);
    }

    public Employee getEmployeeById(long employeeId) {
    	return  this.<Employee>getDataProviderForType().get(Employee.class, employeeId);
    }
    
    public void deleteEmployee(Employee employee){
    	 this.<Employee>getDataProviderForType().delete(employee);
    }

    public Employee addLogbookEntry(Employee employee, LogbookEntry... entries) {
        for (LogbookEntry entry : entries) {
        	employee.addLogbookEntry(entry);
        }
        
        return this.<Employee>getDataProviderForType().update(employee);  
    }

    public Employee assignProjectsToEmployee(Employee employee, Project... projects) {
        for (Project project : projects) {
        	employee.addProject(project);
        }
        
        return this.<Employee>getDataProviderForType().update(employee);    
    }
  
    public List<Employee> getAllEmployees() {
    	return  this.<Employee>getDataProviderForType().getAll(Employee.class);
    }
    
    public LogbookEntry getLogbookEntryById(long id) {
		return  this.<LogbookEntry>getDataProviderForType().get(LogbookEntry.class, id);
	}

    public List<LogbookEntry> getAllLogbookEntries() {
    	return  this.<LogbookEntry>getDataProviderForType().getAll(LogbookEntry.class);
    }
    
    public Phase createPhase(String name) {
    	Phase phase = new Phase(name);
    	this.<Phase>getDataProviderForType().create(phase);
    	return phase;
    }

    public Project createProject(String name, Employee leader, List<Employee> members, List<Module> modules) {
        Project project = new Project(name,leader);
        if(members != null) {
            members.stream().forEach(x -> project.addMember(x));
        }
        if(modules != null) {
            modules.stream().forEach(x -> project.addModule(x));
        }
        this.<Project>getDataProviderForType().create(project);
        return project;

    }
    
    public Project getProjectById(long id) {
        return this.<Project>getDataProviderForType().get(Project.class, id);
    }

	public List<Module> getAllModules() {
    	return  this.<Module>getDataProviderForType().getAll(Module.class);
	}
	
	public List<Address> getAllAddresses() {
    	return  this.<Address>getDataProviderForType().getAll(Address.class);
	}
	
	public Address getAddressById(Long id) {
    	return  this.<Address>getDataProviderForType().get(Address.class, id);
	}
	
	public List<Phase> getAllPhases() {
    	return  this.<Phase>getDataProviderForType().getAll(Phase.class);
	}
	
	public List<Project> getAllProjects() {
    	return  this.<Project>getDataProviderForType().getAll(Project.class);
	}
}