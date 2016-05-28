package swt6.orm.domain.jpa;

import java.util.Date;

import javax.persistence.*;

import swt6.orm.domain.Address;
import swt6.orm.domain.PermanentEmployee;

@Entity
@DiscriminatorValue("P")
public class JpaPermanentEmployee extends JpaEmployee implements PermanentEmployee {
	private static final long serialVersionUID = 1L;
	private double salary;

	public JpaPermanentEmployee() {
	}

	public JpaPermanentEmployee(String firstName, String lastName, Date dateOfBirth) {
		super(firstName, lastName, dateOfBirth);
	}

	public JpaPermanentEmployee(String firstName, String lastName, Date dateOfBirth, Address address) {
		super(firstName, lastName, dateOfBirth, address);
	}

	@Override
	public double getSalary() {
		return salary;
	}

	@Override
	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String toString() {
		return super.toString() + ", salary=" + salary;
	}
}
