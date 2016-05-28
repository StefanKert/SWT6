package swt6.spring.worklog.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Employee implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long     id;
  private String   firstName;
  private String   lastName;
  private Date     dateOfBirth;
  private Set<LogbookEntry> logbookEntries = new HashSet<LogbookEntry>();

  public Employee() {  
  }
  
  public Employee(String firstName, String lastName, Date dateOfBirth) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
  }

  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }
  
 public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth; 
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Set<LogbookEntry> getLogbookEntries() {
    return logbookEntries;
  }

  public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
    this.logbookEntries = logbookEntries;
  }
  
  public void addLogbookEntry(LogbookEntry entry) {
    // If entry is already linked to some employee,
    // remove this link, because we do not want to
    // have an entry linked to different employees.
    if (entry.getEmployee() != null)
       entry.getEmployee().logbookEntries.remove(entry);
    
    // Set a birerection link between entry and this employee.
    this.logbookEntries.add(entry);
    entry.setEmployee(this);
  }

  public String toString() {
    DateFormat fmt = DateFormat.getDateInstance();
    StringBuffer sb = new StringBuffer();
    sb.append(id + ": " + lastName + ", " + firstName + " (" + fmt.format(dateOfBirth.getTime()) + ")" );
    
    return sb.toString();
  }
}