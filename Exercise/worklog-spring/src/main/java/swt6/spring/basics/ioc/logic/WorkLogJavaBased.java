package swt6.spring.basics.ioc.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.util.Log;
import swt6.spring.basics.ioc.util.Logger;
import swt6.spring.basics.ioc.util.LoggerType;

@Component("workLog")
public class WorkLogJavaBased implements WorkLogFacade {
  // only for testing purposes.
  private Map<Long, Employee> employees = new HashMap<Long, Employee>();

  @Inject
  @Log(LoggerType.FILE)
  private Logger logger;
  
  private void init() {
    employees.put(1L, new Employee(1L, "Bill", "Gates"));
    employees.put(2L, new Employee(2L, "James", "Goslin"));
    employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
  }
  
  public WorkLogJavaBased() {
    init();
  }
  
  public WorkLogJavaBased(Logger logger) {
    init();
    this.logger = logger;
  }

  public void setLogger(Logger logger) {
    this.logger = logger;
  }

  public Employee findEmployeeById(Long id) {
    Employee empl = employees.get(id);
    logger.log("findEmployeeById(" + id + ") --> " + 
               ((empl!=null) ? empl.toString() : "<null>"));
    return empl;
  }

  public List<Employee> findAllEmployees() {
    logger.log("findAllEmployees()");
    
    return new ArrayList<Employee>(employees.values());
  }
}