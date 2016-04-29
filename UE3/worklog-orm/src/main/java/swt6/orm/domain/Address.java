package swt6.orm.domain;

public interface Address extends EntityBase {

	String getCity();

	void setCity(String city);

	String getStreet();

	void setStreet(String street);

	String getZipCode();

	void setZipCode(String zipCode);

	Employee getEmployee();

	void setEmployee(Employee employee);
}