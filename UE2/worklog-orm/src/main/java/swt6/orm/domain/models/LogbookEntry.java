package swt6.orm.domain.models;

import java.text.DateFormat;
import java.util.Date;

import javax.persistence.*;

import swt6.orm.domain.models.Employee;
import swt6.orm.domain.models.LogbookEntry;
import swt6.orm.domain.models.Phase;

@Entity
public class LogbookEntry implements IEntity {
	@Id
	@GeneratedValue
	private Long id;
	private String activity;

	@Temporal(TemporalType.TIME)
	private Date startTime;

	@Temporal(TemporalType.TIME)
	private Date endTime;

	@ManyToOne(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.EAGER, optional = false, targetEntity = Employee.class)
	private Employee employee;

	@ManyToOne(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.EAGER, optional = false, targetEntity = Phase.class)
	private Phase phase;

	@ManyToOne(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.EAGER, optional = false, targetEntity = Module.class)
	private Module module;

	public LogbookEntry() {
	}

	public LogbookEntry(String activity, Date start, Date end) {
		this.activity = activity;
		this.startTime = start;
		this.endTime = end;
	}

	public Long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date start) {
		this.startTime = start;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date end) {
		this.endTime = end;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void attachEmployee(Employee employee) {
		if (employee == null)
			throw new IllegalArgumentException("Given employee must not be null.");

		if (this.employee != null)
			this.employee.getLogbookEntries().remove(this);
		if (employee != null)
			employee.getLogbookEntries().add(this);

		this.employee = employee;
	}

	public void detachEmployee() {
		if (this.employee != null)
			this.employee.getLogbookEntries().remove(this);

		this.employee = null;
	}

	public void attachModule(Module module) {
		if (module == null)
			throw new IllegalArgumentException("Given module must not be null.");

		if (this.module != null)
			this.getModule().getLogbookEntries().remove(this);

		this.module = module;
		this.module.getLogbookEntries().add(this);
	}

	public void detachModule() {
		if (this.module != null) {
			this.getModule().getLogbookEntries().remove(this);
		}
		this.module = null;
	}

	public void attachPhase(Phase phase) {
		if (phase == null)
			throw new IllegalArgumentException("Given Phase must not be null.");

		if (this.phase != null)
			this.phase.getLogbookEntries().add(this);

		this.phase = phase;
	}

	public void detachPhase() {
		if (this.phase != null) {
			this.getPhase().getLogbookEntries().remove(this);
		}
		this.phase = null;
	}

	public String toString() {
		DateFormat fmt = DateFormat.getDateTimeInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(activity + ": from " + fmt.format(startTime) + " until" + fmt.format(endTime));
		sb.append(" (" + getEmployee().getLastName() + ");");
		sb.append(" Status: " + getPhase().getName() + "; Module: " + getModule().getName() + " (" + getModule().getProject().getName() + ")");
		return sb.toString();
	}
}