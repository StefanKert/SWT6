package swt6.orm.domain.hibernate;

import java.text.DateFormat;
import java.util.Date;

import swt6.orm.domain.TemporaryEmployee;

public class HibernateTemporaryEmployee extends HibernateEmployee implements TemporaryEmployee {
  private static final long serialVersionUID = 1L;
  private static final DateFormat fmt = DateFormat.getDateInstance();
  
  private String renter;
  private double hourlyRate;
  private Date   startDate;
  private Date   endDate;
   
  public HibernateTemporaryEmployee() {
  }
  
  public HibernateTemporaryEmployee(String firstName, String lastName, Date dateOfBirth) {
    super(firstName, lastName, dateOfBirth);
  }
  
  public HibernateTemporaryEmployee(String firstName, String lastName, Date dateOfBirth, HibernateAddress address) {
    super(firstName, lastName, dateOfBirth, address);
  }

  public Date getEndDate() {
    return endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
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
  
  public Date getStartDate() {
    return startDate;
  }
  
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
