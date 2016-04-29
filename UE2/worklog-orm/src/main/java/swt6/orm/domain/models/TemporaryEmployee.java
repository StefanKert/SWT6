package swt6.orm.domain.models;

import java.text.DateFormat;
import java.util.Date;

import javax.persistence.*;

import swt6.orm.domain.models.Address;

@Entity
@DiscriminatorValue("T")
public class TemporaryEmployee extends Employee{
	private static final DateFormat fmt = DateFormat.getDateInstance();

	private double hourlyRate;
	private String renter;
	private Date startDate;
	private Date endDate;

	public TemporaryEmployee() {
	}

	public TemporaryEmployee(String firstName, String lastName, Date dateOfBirth) {
		super(firstName, lastName, dateOfBirth);
	}

	public TemporaryEmployee(String firstName, String lastName, Date dateOfBirth, Address address) {
		super(firstName, lastName, dateOfBirth, address);
	}
	
	public double getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
	public String getRenter() {
		return renter;
	}

	public void setRenter(String renter) {
		this.renter = renter;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		sb.append(", hourlyRate=" + hourlyRate);
		sb.append(", renter=" + hourlyRate);
		sb.append(", startDate=" + fmt.format(startDate));
		sb.append(", endDate=" + fmt.format(endDate));
		return sb.toString();
	}
}
