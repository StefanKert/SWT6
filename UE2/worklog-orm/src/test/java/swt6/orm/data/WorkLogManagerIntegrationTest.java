package swt6.orm.data;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import swt6.orm.domain.WorkLogManager;
import swt6.orm.domain.models.Address;
import swt6.orm.domain.models.Employee;
import swt6.orm.domain.models.LogbookEntry;
import swt6.orm.domain.models.Module;
import swt6.orm.domain.models.Phase;
import swt6.orm.domain.models.Project;
import swt6.util.DateUtil;

public abstract class WorkLogManagerIntegrationTest {
	protected abstract WorkLogManager getWorkLogManager();

	//This test creats an employe and checks if all data is correctly written to the database
	@Test
	public void testCreate_Employee() {
		WorkLogManager workLogManager = getWorkLogManager();
		Employee employee = new Employee("TestName", "Testnameweiter", DateUtil.getDate(2015, 12, 2));
		Long id = workLogManager.createEmployee(employee);
		Employee employeeInDb = workLogManager.getEmployeeById(id);
		Assert.assertEquals(employee.getFirstName(), employeeInDb.getFirstName());
		Assert.assertEquals(employee.getLastName(), employeeInDb.getLastName());
		Assert.assertEquals(employee.getDateOfBirth(), employeeInDb.getDateOfBirth());
		workLogManager.deleteEmployee(employeeInDb);
	}
	
	//This test creats a employee updates the firstname and stores it into the database and checks if the data is updated successfully
	@Test
	public void testUpdate_Employee() {
		WorkLogManager workLogManager = getWorkLogManager();
		Employee employee = new Employee("TestName", "Testnameweiter", DateUtil.getDate(2015, 12, 2));
		Long id = workLogManager.createEmployee(employee);
		Employee employeeInDb = workLogManager.getEmployeeById(id);
		employeeInDb.setFirstName("Neuer Name");
		workLogManager.updateEmployee(employeeInDb);
		Employee updatedEmployeeInDb = workLogManager.getEmployeeById(id);
		Assert.assertEquals("Neuer Name", updatedEmployeeInDb.getFirstName());
		Assert.assertEquals(employee.getLastName(), updatedEmployeeInDb.getLastName());
		Assert.assertEquals(employee.getDateOfBirth(), updatedEmployeeInDb.getDateOfBirth());
		workLogManager.deleteEmployee(updatedEmployeeInDb);
	}
	
	// This test creates an employee and tests if deleting it, cascades the delete and deletes address
	@Test
	public void testDeletingEmployee_ShouldCascadeDeletingAddress() {
		WorkLogManager workLogManager = getWorkLogManager();
		Employee employee = new Employee("TestName", "Testnameweiter", DateUtil.getDate(2015, 12, 2));
		Long id = workLogManager.createEmployee(employee);
		Employee employeeInDb = workLogManager.getEmployeeById(id);
		Address address = new Address("TestZip", "TestCity", "TestStreet");
		employeeInDb.setAddress(address);
		workLogManager.updateEmployee(employeeInDb);
		Employee updatedEmployeeInDb = workLogManager.getEmployeeById(id);
		Assert.assertEquals(address.getCity(), updatedEmployeeInDb.getAddress().getCity());
		Assert.assertEquals(address.getStreet(), updatedEmployeeInDb.getAddress().getStreet());
		Assert.assertEquals(address.getZipCode(), updatedEmployeeInDb.getAddress().getZipCode());

		workLogManager.deleteEmployee(updatedEmployeeInDb);
		
		Assert.assertNull(workLogManager.getAddressById(updatedEmployeeInDb.getAddress().getId()));
	}
	
	// This test creates an employee and checks if it is deleted correclty
	@Test
	public void testDelete_Employee() {
		WorkLogManager workLogManager = getWorkLogManager();
		Employee employee = new Employee("TestName", "Testnameweiter", DateUtil.getDate(2015, 12, 2));
		Long id = workLogManager.createEmployee(employee);
		Employee employeeInDb = workLogManager.getEmployeeById(id);
		workLogManager.deleteEmployee(employeeInDb);
		Employee probablyDeletedEmployee = workLogManager.getEmployeeById(id);
		Assert.assertNull(probablyDeletedEmployee);
	}

	// This test returns all employees that are stored in the database
	@Test
	public void testGetAll_Employees_ShouldReturn_10() {
		WorkLogManager workLogManager = getWorkLogManager();
		List<Employee> entries = workLogManager.getAllEmployees();
		Assert.assertSame(entries.size(), 10);
	}

	//This test returns all logbookentries that are stored in the database
	@Test
	public void testGetAll_LogbookEntry_ShouldReturn_50() {
		WorkLogManager workLogManager = getWorkLogManager();
		List<LogbookEntry> entries = workLogManager.getAllLogbookEntries();
		Assert.assertSame(entries.size(), 50);
	}
	
	//This test returns all phases that are stored in the database
	@Test
	public void testGetAll_Phases_ShouldReturn_50() {
		WorkLogManager workLogManager = getWorkLogManager();
		List<Phase> entries = workLogManager.getAllPhases();
		Assert.assertSame(entries.size(), 50);
	}

	//This test returns all projects that are stored in the database
	@Test
	public void testGetAll_Projects_ShouldReturn_50() {
		WorkLogManager workLogManager = getWorkLogManager();
		List<Project> entries = workLogManager.getAllProjects();
		Assert.assertSame(entries.size(), 50);
	}

	//This test returns all modules that are stored in the database
	@Test
	public void testGetAll_Modules_ShouldReturn_50() {
		WorkLogManager workLogManager = getWorkLogManager();
		List<Module> entries = workLogManager.getAllModules();
		Assert.assertSame(entries.size(), 50);
	}

	//This test returns all addresses that are stored in the database
	@Test
	public void testGetAll_Addresses_ShouldReturn_10() {
		WorkLogManager workLogManager = getWorkLogManager();
		List<Address> entries = workLogManager.getAllAddresses();
		Assert.assertSame(entries.size(), 10);
	}
}