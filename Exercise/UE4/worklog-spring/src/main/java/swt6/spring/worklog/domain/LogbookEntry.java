package swt6.spring.worklog.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class LogbookEntry implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final DateFormat fmt = DateFormat.getDateTimeInstance();

  private Long     id;
  private String   activity;
  private Date     startTime;
  private Date     endTime;
  private Employee employee;
  
  public LogbookEntry() {
  }
  
  public LogbookEntry(String activity, Date start, Date end) {
    this.activity = activity;
    this.startTime = start;
    this.endTime = end;
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getActivity() {
    return activity;
  }
  
  public void setActivity(String activity) {
    this.activity = activity;
  }
  
  public Employee getEmployee() {
    return employee;
  }
  
  // setEmployee ist also invoked when log Entries are being
  // loades lazily. This cases an exception.
  public void setEmployee(Employee employee) {
    this.employee = employee;
  }
  
  public void assignEmployee(Employee employee) {
    // If this entry is already linked to some employee,
    // remove this link.
    if (this.employee != null)
      this.employee.getLogbookEntries().remove(this);
    
    // Add a bidirection link between this entry and employee.
    if (employee != null)
       employee.getLogbookEntries().add(this);
    this.employee = employee;
  }
  
  public Date getEndTime() {
    return endTime;
  }
  
  public void setEndTime(Date end) {
    this.endTime = end;
  }
  
  public Date getStartTime() {
    return startTime;
  }
  
  public void setStartTime(Date start) {
    this.startTime = start;
  }
  
  @Override
  public String toString() {
    return activity + ": " 
           + fmt.format(startTime) + " - " 
           + fmt.format(endTime) + " ("
           + getEmployee().getLastName() + ")";

  }
}
