package swt6.orm.domain.hibernate;

import java.text.DateFormat;
import java.util.Date;

import swt6.orm.domain.Employee;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Module;
import swt6.orm.domain.Phase;

public class HibernateLogbookEntry implements LogbookEntry {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String activity;
	private Date startTime;
	private Date endTime;
	private Employee employee;
	private Phase phase;
	private Module module;

	public HibernateLogbookEntry() {
	}

	public HibernateLogbookEntry(String activity, Date start, Date end) {
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void attachEmployee(Employee employee) {
		if (employee == null)
			throw new IllegalArgumentException("Employee must not be null.");

		if (this.employee != null)
			this.employee.getLogbookEntries().remove(this);

		this.employee = employee;
		this.employee.getLogbookEntries().add(this);
	}

	public void detachEmployee() {
		if (this.employee != null)
			this.employee.getLogbookEntries().remove(this);

		this.employee = null;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date end) {
		this.endTime = end;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date start) {
		this.startTime = start;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public void attachPhase(Phase phase) {
		if (phase == null)
			throw new IllegalArgumentException("Phase must not be null.");

		if (this.phase != null)
			this.getPhase().getLogbookEntries().remove(this);

		this.phase = phase;
		this.phase.getLogbookEntries().add(this);
	}
	
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void attachModule(Module module) {
		if (module == null)
			throw new IllegalArgumentException("Module must not be null.");

		if (this.module != null)
			this.getModule().getLogbookEntries().remove(this);

		this.module = module;
		this.module.getLogbookEntries().add(this);
	}
	
	@Override
	public String toString() {
		DateFormat fmt = DateFormat.getDateTimeInstance();
		return activity + ": " + fmt.format(startTime) + " - " + fmt.format(endTime) + " ("
				+ getEmployee().getLastName() + "); Status: " + getPhase().getName() + "; Module: "
				+ getModule().getName();
	}
}
