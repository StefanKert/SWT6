package swt6.orm.test;

import org.junit.BeforeClass;

import swt6.orm.dao.hibernate.HibernateDao;
import swt6.orm.domain.Address;
import swt6.orm.domain.Employee;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Module;
import swt6.orm.domain.PermanentEmployee;
import swt6.orm.domain.Phase;
import swt6.orm.domain.Project;
import swt6.orm.domain.TemporaryEmployee;
import swt6.orm.domain.hibernate.HibernateAddress;
import swt6.orm.domain.hibernate.HibernateEmployee;
import swt6.orm.domain.hibernate.HibernateLogbookEntry;
import swt6.orm.domain.hibernate.HibernateModule;
import swt6.orm.domain.hibernate.HibernatePermanentEmployee;
import swt6.orm.domain.hibernate.HibernatePhase;
import swt6.orm.domain.hibernate.HibernateProject;
import swt6.orm.domain.hibernate.HibernateTemporaryEmployee;
import swt6.orm.test.defaults.TestConstants;
import swt6.util.DateUtil;

public class HibernateIntegrationTest extends IntegrationTestBase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		employeeDao = new HibernateDao<>();
		addressDao = new HibernateDao<>();
		employeeDao = new HibernateDao<>();
		logbookEntryDao = new HibernateDao<>();
		moduleDao = new HibernateDao<>();
		phaseDao = new HibernateDao<>();
		projectDao = new HibernateDao<>();
		
		initializeData();
	}
	
	private static void initializeData() {
		for (int i = 1; i <= TestConstants.EMPLOYEE_COUNT; i++) {
			Employee empl;
			if (i % 3 == 0) {
				empl = new HibernatePermanentEmployee(TestConstants.FIRSTNAME_PREFIX + i, TestConstants.LASTNAME_PREFIX + i, DateUtil.getDate(1980, 1, i));
				((PermanentEmployee) empl).setSalary(100.0 * i);
			} else if (i % 3 == 1) {
				empl = new HibernateTemporaryEmployee(TestConstants.FIRSTNAME_PREFIX + i, TestConstants.LASTNAME_PREFIX + i, DateUtil.getDate(1980, 1, i));
				((TemporaryEmployee) empl).setHourlyRate(10.0 * i);
				((TemporaryEmployee) empl).setRenter("Renter" + i);
			} else {
				empl = new HibernateEmployee(TestConstants.FIRSTNAME_PREFIX + i, TestConstants.LASTNAME_PREFIX + i, DateUtil.getDate(1980, 1, i));
			}

			empl.setAddress(new HibernateAddress(TestConstants.ADDRESS_ZIP, TestConstants.ADDRESS_STREET, TestConstants.ADDRESS_STREET + i));

			for (int j = 1; j <= TestConstants.LOGBOOKENTRY_COUNT; j++) {
				LogbookEntry lbe = new HibernateLogbookEntry(TestConstants.LOGBOOKENTRY_ACTIVITY, DateUtil.getTime(8, 45),
						DateUtil.getTime(17, 15));

				lbe.attachPhase(new HibernatePhase(TestConstants.PHASE_NAME));
				
				Module module = new HibernateModule(TestConstants.MODULE_NAME);
				module.setProject(new HibernateProject(TestConstants.PROJECT_NAME));
				lbe.attachModule(module);

				empl.addLogbookEntry(lbe);
			}
			
			employeeDao.create(empl);
		}
	}

	@Override
	protected Class<? extends Address> getAddressClass() {
		return HibernateAddress.class;
	}

	@Override
	protected Class<? extends Employee> getEmployeeClass() {
		return HibernateEmployee.class;
	}

	@Override
	protected Class<? extends LogbookEntry> getLogbookEntryClass() {
		return HibernateLogbookEntry.class;
	}

	@Override
	protected Class<? extends Module> getModuleClass() {
		return HibernateModule.class;
	}

	@Override
	protected Class<? extends Phase> getPhaseClass() {
		return HibernatePhase.class;
	}

	@Override
	protected Class<? extends Project> getProjectClass() {
		return HibernateProject.class;
	}

	@Override
	protected Address getNewAddress() {
		return new HibernateAddress("5020", "Salzburg", "Straße 1");
	}

	@Override
	protected Employee getNewEmployee() {
		return new HibernateEmployee("Tom", "Schmiedlechner", DateUtil.getDate(1992, 1, 1));
	}

	@Override
	protected LogbookEntry getNewLogbookEntry() {
		return new HibernateLogbookEntry("My Activity",DateUtil.getTime(8, 45),
						DateUtil.getTime(17, 15));
	}

	@Override
	protected Module getNewModule() {
		return new HibernateModule("My Module");
	}

	@Override
	protected Phase getNewPhase() {
		return new HibernatePhase("My Phase");
	}

	@Override
	protected Project getNewProject() {
		return new HibernateProject("My Project");
	}
}
