package swt6.orm.domain.models;

import java.util.Date;

import javax.persistence.*;

import swt6.orm.domain.models.Address;

@Entity
@DiscriminatorValue("P")
public class PermanentEmployee extends Employee {
	private double salary;

	public PermanentEmployee() {
	}

	public PermanentEmployee(String firstName, String lastName, Date dateOfBirth) {
		super(firstName, lastName, dateOfBirth);
	}

	public PermanentEmployee(String firstName, String lastName, Date dateOfBirth, Address address) {
		super(firstName, lastName, dateOfBirth, address);
	}
	
	public PermanentEmployee(String firstName, String lastName, Date dateOfBirth, double salary, Address address) {
		super(firstName, lastName, dateOfBirth, address);
		this.salary = salary;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String toString() {
		return super.toString() + ", salary=" + salary;
	}
}
