package swt6.orm.domain.jpa;

import java.text.DateFormat;
import java.util.Date;

import javax.persistence.*;

import swt6.orm.domain.Employee;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Module;
import swt6.orm.domain.Phase;

@Entity
public class JpaLogbookEntry implements LogbookEntry {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String activity;

	@Temporal(TemporalType.TIME)
	private Date startTime;

	@Temporal(TemporalType.TIME)
	private Date endTime;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = false, targetEntity = JpaPhase.class)
	private Phase phase;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = false, targetEntity = JpaModule.class)
	private Module module;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER, optional = false, targetEntity = JpaEmployee.class)
	private Employee employee;

	public JpaLogbookEntry() {
	}

	public JpaLogbookEntry(String activity, Date start, Date end) {
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

	@Override
	public String getActivity() {
		return activity;
	}

	@Override
	public void setActivity(String activity) {
		this.activity = activity;
	}

	@Override
	public Employee getEmployee() {
		return employee;
	}

	// setEmployee is also invoked when logbook entries are being
	// loaded lazily. This causes an exception.
	@Override
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public void attachEmployee(Employee employee) {
		if (employee == null)
			throw new IllegalArgumentException("Employee must not be null.");

		if (this.employee != null)
			this.employee.getLogbookEntries().remove(this);

		this.employee = employee;
		this.employee.getLogbookEntries().add(this);
	}

	@Override
	public void detachEmployee() {
		if (this.employee != null)
			this.employee.getLogbookEntries().remove(this);

		this.employee = null;
	}

	@Override
	public Date getEndTime() {
		return endTime;
	}

	@Override
	public void setEndTime(Date end) {
		this.endTime = end;
	}

	@Override
	public Date getStartTime() {
		return startTime;
	}

	@Override
	public void setStartTime(Date start) {
		this.startTime = start;
	}

	@Override
	public Module getModule() {
		return module;
	}

	@Override
	public void setModule(Module module) {
		this.module = module;
	}

	@Override
	public void attachModule(Module module) {
		if (module == null)
			throw new IllegalArgumentException("Module must not be null.");

		if (this.module != null)
			this.getModule().getLogbookEntries().remove(this);

		this.module = module;
		this.module.getLogbookEntries().add(this);
	}

	@Override
	public Phase getPhase() {
		return phase;
	}

	@Override
	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	@Override
	public void attachPhase(Phase phase) {
		if (phase == null)
			throw new IllegalArgumentException("Phase must not be null.");

		if (this.phase != null)
			this.getPhase().getLogbookEntries().remove(this);

		this.phase = phase;
		this.phase.getLogbookEntries().add(this);
	}

	@Override
	public String toString() {
		DateFormat fmt = DateFormat.getDateTimeInstance();
		return activity + ": " + fmt.format(startTime) + " - " + fmt.format(endTime) + " ("
				+ getEmployee().getLastName() + "); Status: " + getPhase().getName() + "; Module: "
				+ getModule().getName() + " (" + getModule().getProject().getName() + ")";

	}
}
