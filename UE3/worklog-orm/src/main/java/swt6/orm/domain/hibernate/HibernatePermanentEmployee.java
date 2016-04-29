package swt6.orm.domain.hibernate;

import java.util.Date;

import swt6.orm.domain.PermanentEmployee;

public class HibernatePermanentEmployee extends HibernateEmployee implements PermanentEmployee {
  private static final long serialVersionUID = 1L;
  private double salary;

  public HibernatePermanentEmployee() {  
  }
  
  public HibernatePermanentEmployee(String firstName, String lastName, Date dateOfBirth) {
    super(firstName, lastName, dateOfBirth);
  }
  
  public HibernatePermanentEmployee(String firstName, String lastName, Date dateOfBirth, HibernateAddress address) {
    super(firstName, lastName, dateOfBirth, address);
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
