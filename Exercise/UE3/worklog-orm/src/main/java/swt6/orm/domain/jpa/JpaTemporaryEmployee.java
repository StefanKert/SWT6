package swt6.orm.domain.jpa;

import java.text.DateFormat;
import java.util.Date;

import javax.persistence.*;

import swt6.orm.domain.Address;
import swt6.orm.domain.TemporaryEmployee;

@Entity
@DiscriminatorValue("T")
public class JpaTemporaryEmployee extends JpaEmployee implements TemporaryEmployee {
	private static final long serialVersionUID = 1L;
	private static final DateFormat fmt = DateFormat.getDateInstance();

	private String renter;
	private double hourlyRate;
	private Date startDate;
	private Date endDate;

	public JpaTemporaryEmployee() {
	}

	public JpaTemporaryEmployee(String firstName, String lastName, Date dateOfBirth) {
		super(firstName, lastName, dateOfBirth);
	}

	public JpaTemporaryEmployee(String firstName, String lastName, Date dateOfBirth, Address address) {
		super(firstName, lastName, dateOfBirth, address);
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	@Override
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public double getHourlyRate() {
		return hourlyRate;
	}

	@Override
	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	@Override
	public String getRenter() {
		return renter;
	}

	@Override
	public void setRenter(String renter) {
		this.renter = renter;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
