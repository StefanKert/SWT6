package swt6.orm.test;

import org.junit.BeforeClass;

import swt6.orm.dao.jpa.JpaDao;
import swt6.orm.domain.Address;
import swt6.orm.domain.Employee;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Module;
import swt6.orm.domain.PermanentEmployee;
import swt6.orm.domain.Phase;
import swt6.orm.domain.Project;
import swt6.orm.domain.TemporaryEmployee;
import swt6.orm.domain.jpa.JpaAddress;
import swt6.orm.domain.jpa.JpaEmployee;
import swt6.orm.domain.jpa.JpaLogbookEntry;
import swt6.orm.domain.jpa.JpaModule;
import swt6.orm.domain.jpa.JpaPermanentEmployee;
import swt6.orm.domain.jpa.JpaPhase;
import swt6.orm.domain.jpa.JpaProject;
import swt6.orm.domain.jpa.JpaTemporaryEmployee;
import swt6.orm.test.defaults.TestConstants;
import swt6.util.DateUtil;

public class JPAIntegrationTest extends IntegrationTestBase {
		
	@BeforeClass
	public static void setUpClass() throws Exception {
		employeeDao = new JpaDao<>();
		addressDao = new JpaDao<>();
		employeeDao = new JpaDao<>();
		logbookEntryDao = new JpaDao<>();
		moduleDao = new JpaDao<>();
		phaseDao = new JpaDao<>();
		projectDao = new JpaDao<>();
		
		initializeData();
	}
	
	private static void initializeData() {
		for (int i = 1; i <= TestConstants.EMPLOYEE_COUNT; i++) {
			Employee empl;
			if (i % 3 == 0) {
				empl = new JpaPermanentEmployee(TestConstants.FIRSTNAME_PREFIX + i, TestConstants.LASTNAME_PREFIX + i, DateUtil.getDate(1980, 1, i));
				((PermanentEmployee) empl).setSalary(100.0 * i);
			} else if (i % 3 == 1) {
				empl = new JpaTemporaryEmployee(TestConstants.FIRSTNAME_PREFIX + i, TestConstants.LASTNAME_PREFIX + i, DateUtil.getDate(1980, 1, i));
				((TemporaryEmployee) empl).setHourlyRate(10.0 * i);
				((TemporaryEmployee) empl).setRenter("Renter" + i);
			} else {
				empl = new JpaEmployee(TestConstants.FIRSTNAME_PREFIX + i, TestConstants.LASTNAME_PREFIX + i, DateUtil.getDate(1980, 1, i));
			}

			empl.setAddress(new JpaAddress(TestConstants.ADDRESS_ZIP, TestConstants.ADDRESS_STREET, TestConstants.ADDRESS_STREET + i));

			for (int j = 1; j <= TestConstants.LOGBOOKENTRY_COUNT; j++) {
				LogbookEntry lbe = new JpaLogbookEntry(TestConstants.LOGBOOKENTRY_ACTIVITY, DateUtil.getTime(8, 45),
						DateUtil.getTime(17, 15));

				lbe.attachPhase(new JpaPhase(TestConstants.PHASE_NAME));
				
				Module module = new JpaModule(TestConstants.MODULE_NAME);
				module.setProject(new JpaProject(TestConstants.PROJECT_NAME));
				lbe.attachModule(module);

				empl.addLogbookEntry(lbe);
			}

			employeeDao.create(empl);
		}
	}	

	@Override
	protected Class<? extends Address> getAddressClass() {
		return JpaAddress.class;
	}

	@Override
	protected Class<? extends Employee> getEmployeeClass() {
		return JpaEmployee.class;
	}

	@Override
	protected Class<? extends LogbookEntry> getLogbookEntryClass() {
		return JpaLogbookEntry.class;
	}

	@Override
	protected Class<? extends Module> getModuleClass() {
		return JpaModule.class;
	}

	@Override
	protected Class<? extends Phase> getPhaseClass() {
		return JpaPhase.class;
	}

	@Override
	protected Class<? extends Project> getProjectClass() {
		return JpaProject.class;
	}

	@Override
	protected Address getNewAddress() {
		return new JpaAddress("5020", "Salzburg", "Straße 1");
	}

	@Override
	protected Employee getNewEmployee() {
		return new JpaEmployee("Tom", "Schmiedlechner", DateUtil.getDate(1992, 1, 1));
	}

	@Override
	protected LogbookEntry getNewLogbookEntry() {
		return new JpaLogbookEntry("My Activity",DateUtil.getTime(8, 45),
						DateUtil.getTime(17, 15));
	}

	@Override
	protected Module getNewModule() {
		return new JpaModule("My Module");
	}

	@Override
	protected Phase getNewPhase() {
		return new JpaPhase("My Phase");
	}

	@Override
	protected Project getNewProject() {
		return new JpaProject("My Project");
	}
}
