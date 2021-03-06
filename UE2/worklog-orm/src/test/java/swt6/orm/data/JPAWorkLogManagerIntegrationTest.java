package swt6.orm.data;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import swt6.orm.data.jpa.JPADataProvider;
import swt6.orm.data.jpa.JPAUtil;
import swt6.orm.domain.JPAWorkLogManager;
import swt6.orm.domain.WorkLogManager;
import swt6.orm.domain.models.Address;
import swt6.orm.domain.models.Employee;
import swt6.orm.domain.models.LogbookEntry;
import swt6.orm.domain.models.Module;
import swt6.orm.domain.models.PermanentEmployee;
import swt6.orm.domain.models.Phase;
import swt6.orm.domain.models.Project;
import swt6.orm.domain.models.TemporaryEmployee;
import swt6.util.DateUtil;

public class JPAWorkLogManagerIntegrationTest extends WorkLogManagerIntegrationTest {
	@BeforeClass
	public static void setUpClass() throws Exception {
		generateData(10);
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		JPAUtil.closeEntityManagerFactory();
	}
	
	
	@Override
	protected WorkLogManager getWorkLogManager(){
		return new JPAWorkLogManager();
	}
	
	protected static void generateData(int amount) {
		for (int i = 1; i <= amount; i++) {
			Employee employee;
			Address address = new Address("TestZipCode" + i, "TestCity" + i, "TestStreet" + i);
			if (i % 3 == 0) {
				employee = new Employee("TesterVorname" + i, "TesterNachname" + i, DateUtil.getDate(1980, 1, i),
						address);
			} else if (i % 3 == 1) {
				employee = new PermanentEmployee("TesterVorname" + i, "TesterNachname" + i,
						DateUtil.getDate(1980, 1, i), (double) i, address);

			} else {
				employee = new TemporaryEmployee("TesterVorname" + i, "TesterNachname" + i,
						DateUtil.getDate(1980, 1, i), address);
				((TemporaryEmployee) employee).setHourlyRate(200.0 * i);
				((TemporaryEmployee) employee).setRenter("TestRenter" + i);
			}

			new JPADataProvider<Employee>().create(employee);

			for (int j = 1; j <= 5; j++) {
				Phase phase = new Phase("Phase" + j);
				new JPADataProvider<Phase>().create(phase);
				Project project = new Project("Project" + j);
				project.attachLeader(employee);
				new JPADataProvider<Project>().create(project);
				Module module = new Module("Module" + j);
				module.setProject(project);
				new JPADataProvider<Module>().create(module);

				LogbookEntry logBookEntry = new LogbookEntry("Activity" + j, DateUtil.getTime(8, 45),
						DateUtil.getTime(17, 15));
				logBookEntry.attachPhase(phase);
				logBookEntry.attachModule(module);
				employee.addLogbookEntry(logBookEntry);
				new JPADataProvider<LogbookEntry>().create(logBookEntry);
			}

			new JPADataProvider<Employee>().update(employee);
		}
	}
	
}
