package swt6.spring.worklog.web;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import swt6.spring.basics.ioc.util.LoggerFactory;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.logic.WorkLogFacade;

@Controller
public class EmployeeController {
	
	//private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private WorkLogFacade workLog;
	
	@RequestMapping("/employees")
	public String  list(Model model){
		List<Employee> employees = this.workLog.findAllEmployees();
		
		model.addAttribute("employees", employees);
		
		return "employeeList";
	}
}
