package swt6.orm.domain;

import java.util.Date;

public interface TemporaryEmployee extends Employee {

	Date getEndDate();

	void setEndDate(Date endDate);

	double getHourlyRate();

	void setHourlyRate(double hourlyRate);

	String getRenter();

	void setRenter(String renter);

	Date getStartDate();

	void setStartDate(Date startDate);

}