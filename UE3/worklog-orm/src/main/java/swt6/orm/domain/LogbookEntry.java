package swt6.orm.domain;

import java.util.Date;

public interface LogbookEntry extends EntityBase {

	String getActivity();

	void setActivity(String activity);

	Employee getEmployee();

	// setEmployee is also invoked when logbook entries are being
	// loaded lazily. This causes an exception.
	void setEmployee(Employee employee);

	void attachEmployee(Employee employee);

	void detachEmployee();

	Date getEndTime();

	void setEndTime(Date end);

	Date getStartTime();

	void setStartTime(Date start);

	Module getModule();

	void setModule(Module module);

	void attachModule(Module module);

	Phase getPhase();

	void setPhase(Phase phase);

	void attachPhase(Phase phase);

}