package swt6.orm.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import swt6.orm.dao.Dao;
import swt6.orm.domain.*;
import swt6.orm.test.defaults.TestConstants;

/**
 * Executes different tests for each base class (in this case, HibernateIntegrationTest and JPAIntegrationTest).
 * Since the test data is created in the initializeData() method, it's enough to validate the data exists.
 * 
 * @author Tom Schmiedlechner
 */
public abstract class IntegrationTestBase {

	protected static Dao<Address> addressDao;
	protected static Dao<Employee> employeeDao;
	protected static Dao<LogbookEntry> logbookEntryDao;
	protected static Dao<Module> moduleDao;
	protected static Dao<Phase> phaseDao;
	protected static Dao<Project> projectDao;
	
	protected abstract Class<? extends Address> getAddressClass();
	protected abstract Class<? extends Employee> getEmployeeClass();
	protected abstract Class<? extends LogbookEntry> getLogbookEntryClass();
	protected abstract Class<? extends Module> getModuleClass();
	protected abstract Class<? extends Phase> getPhaseClass();
	protected abstract Class<? extends Project> getProjectClass();
	

	protected abstract Address getNewAddress();
	protected abstract Employee getNewEmployee();
	protected abstract LogbookEntry getNewLogbookEntry();
	protected abstract Module getNewModule();
	protected abstract Phase getNewPhase();
	protected abstract Project getNewProject();

	/**
	 * Gets all employees and validates that there are as much as created in initializeData.
	 */
	@Test
	public void getAllEmployees() {
		List<Employee> result = employeeDao.getAll(getEmployeeClass());
		Assert.assertNotNull(result);
		Assert.assertTrue(result.size() == TestConstants.EMPLOYEE_COUNT);
	}

	/**
	 * Gets first employee object and validate that it's properties are correctly set.
	 */
	@Test
	public void getEmployeeById() {
		Employee empl = employeeDao.get(getEmployeeClass(), 1);
		Assert.assertNotNull(empl);
		Assert.assertTrue(empl.getFirstName().startsWith(TestConstants.FIRSTNAME_PREFIX));
		Assert.assertTrue(empl.getLastName().startsWith(TestConstants.LASTNAME_PREFIX));
	}

	/**
	 * Creates and deletes an employee and validates if the process was successful.
	 */
	@Test
	public void createAndDeleteEmployee() {
		Employee empl = getNewEmployee();
		
		int countBefore = employeeDao.getAll(getEmployeeClass()).size();
		long id = employeeDao.create(empl);
		Assert.assertTrue(id > 0);
		Assert.assertEquals(employeeDao.getAll(getEmployeeClass()).size(), countBefore + 1);
		
		employeeDao.delete(empl);
		Assert.assertEquals(employeeDao.getAll(getEmployeeClass()).size(), countBefore);		
	}

	/**
	 * Gets all LogbookEntries and validates that there are as much as created in initializeData.
	 */
	@Test
	public void getAllLogbookEntries() {
		List<LogbookEntry> result = logbookEntryDao.getAll(getLogbookEntryClass());
		Assert.assertNotNull(result);
		Assert.assertTrue(result.size() == TestConstants.EMPLOYEE_COUNT * TestConstants.LOGBOOKENTRY_COUNT);
	}

	/**
	 * Gets first LogbookEntry object and validate that it's properties are correctly set.
	 */
	@Test
	public void getLogbookEntryById() {
		LogbookEntry empl = logbookEntryDao.get(getLogbookEntryClass(), 1);
		Assert.assertNotNull(empl);
		Assert.assertEquals(empl.getActivity(), TestConstants.LOGBOOKENTRY_ACTIVITY);
	}

	/**
	 * Gets all projects and validates that there are as much as created in initializeData.
	 */
	@Test
	public void getAllProjects() {
		List<Project> result = projectDao.getAll(getProjectClass());
		Assert.assertNotNull(result);
		Assert.assertTrue(result.size() == TestConstants.EMPLOYEE_COUNT * TestConstants.LOGBOOKENTRY_COUNT);
	}

	/**
	 * Gets first project object and validate that it's properties are correctly set.
	 */
	@Test
	public void getProjectById() {
		Project proj = projectDao.get(getProjectClass(), 1);
		Assert.assertNotNull(proj);
		Assert.assertEquals(proj.getName(), TestConstants.PROJECT_NAME);
	}

	/**
	 * Gets all modules and validates that there are as much as created in initializeData.
	 */
	@Test
	public void getAllModules() {
		List<Module> result = moduleDao.getAll(getModuleClass());
		Assert.assertNotNull(result);
		Assert.assertTrue(result.size() == TestConstants.EMPLOYEE_COUNT * TestConstants.LOGBOOKENTRY_COUNT);
	}

	/**
	 * Gets first module object and validate that it's properties are correctly set.
	 */
	@Test
	public void getModuleById() {
		Module proj = moduleDao.get(getModuleClass(), 1);
		Assert.assertNotNull(proj);
		Assert.assertEquals(proj.getName(), TestConstants.MODULE_NAME);
	}

	/**
	 * Gets all addresses and validates that there are as much as created in initializeData.
	 */
	@Test
	public void getAllAddresses() {
		List<Address> result = addressDao.getAll(getAddressClass());
		Assert.assertNotNull(result);
		Assert.assertTrue(result.size() == TestConstants.EMPLOYEE_COUNT);
	}

	/**
	 * Gets first address object and validate that it's properties are correctly set.
	 */
	@Test
	public void getAddressById() {
		Address addr = addressDao.get(getAddressClass(), 1);
		Assert.assertNotNull(addr);

		Assert.assertEquals(addr.getZipCode(), TestConstants.ADDRESS_ZIP);
		Assert.assertEquals(addr.getCity(), TestConstants.ADDRESS_STREET);
		Assert.assertTrue(addr.getStreet().startsWith(TestConstants.ADDRESS_STREET));
	}
}
