package swt6.orm.domain.models;

import javax.persistence.*;

import swt6.orm.domain.models.Address;
import swt6.orm.domain.models.Employee;

@Entity
public class Address implements IEntity  {
	@Id
	@GeneratedValue
	private Long id;
	private String street;
	private String city;
	private String zipCode;

	private Employee employee;

	public Address() {}

	public Address(String zipCode, String city, String street) {
		this.zipCode = zipCode;
		this.city = city;
		this.street = street;
	}
	
	public Address(String zipCode, String city, String street, Employee employee) {
		this.zipCode = zipCode;
		this.city = city;
		this.street = street;
		this.employee = employee;
	}

	public Long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String toString() {
		return zipCode + " " + city + ", " + street;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}