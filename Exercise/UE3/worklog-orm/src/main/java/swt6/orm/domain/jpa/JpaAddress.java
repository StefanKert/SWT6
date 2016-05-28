package swt6.orm.domain.jpa;

import javax.persistence.*;

import swt6.orm.domain.Address;
import swt6.orm.domain.Employee;

@Entity
public class JpaAddress implements Address {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String zipCode;
	private String city;
	private String street;

	private Employee employee;

	public JpaAddress() {
	}

	public JpaAddress(String zipCode, String city, String street) {
		this.zipCode = zipCode;
		this.city = city;
		this.street = street;
	}

	public Long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getStreet() {
		return street;
	}

	@Override
	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String getZipCode() {
		return zipCode;
	}

	@Override
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String toString() {
		return zipCode + " " + city + ", " + street;
	}

	@Override
	public Employee getEmployee() {
		return employee;
	}

	@Override
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
