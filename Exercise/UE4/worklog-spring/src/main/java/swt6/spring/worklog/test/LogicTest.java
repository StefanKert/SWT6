package swt6.spring.worklog.test;

import javax.persistence.EntityManagerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.logic.WorkLogFacade;
import swt6.util.DateUtil;

import swt6.util.JpaUtil;

public class LogicTest {

	private static Employee empl1;
	private static Employee empl2;
	private static Employee empl3;

	private static void testSaveEmployee(WorkLogFacade workLog) {
		empl1 = new Employee("Sepp", "Forcher", DateUtil.getDate(1935, 12, 12));
		empl2 = new Employee("Alfred", "Kunz", DateUtil.getDate(1944, 8, 10));
		empl3 = new Employee("Sigfried", "Hinz", DateUtil.getDate(1954, 5, 3));

		empl1 = workLog.syncEmployee(empl1);
		empl2 = workLog.syncEmployee(empl2);
		empl3 = workLog.syncEmployee(empl3);
	}

	private static void testAddLogbookEntry(WorkLogFacade workLog) {
		LogbookEntry entry1 = new LogbookEntry("Analyse", DateUtil.getTime(10, 0), DateUtil.getTime(13, 45));
		LogbookEntry entry2 = new LogbookEntry("Implementierung", DateUtil.getTime(10, 15), DateUtil.getTime(14, 30));
		LogbookEntry entry3 = new LogbookEntry("Testen", DateUtil.getTime(10, 15), DateUtil.getTime(14, 30));

		empl1.addLogbookEntry(entry1);
		empl1.addLogbookEntry(entry2);
		empl2.addLogbookEntry(entry3);

		empl1 = workLog.syncEmployee(empl1);
		empl2 = workLog.syncEmployee(empl2);
	}

	private static void testFindById(WorkLogFacade workLog) {
		Employee empl = workLog.findEmployeeById(empl1.getId());
		System.out.println("empl=" + (empl == null ? (null) : empl.toString()));
	}

	private static void testFindAll(WorkLogFacade workLog) {
		for (Employee e : workLog.findAllEmployees()) {
			System.out.println(e);
			for (LogbookEntry entry : e.getLogbookEntries())
				System.out.println("   " + entry.getId() + ": " + entry);
		}
	}

	private static void testBusinessLogicWithJpaDaos() {
		System.out.println("==================================================================");
		System.out.println("================= testBusinessLogicWithJpaDaos ===================");
		System.out.println("==================================================================");
		
		try (AbstractApplicationContext appCtx = new ClassPathXmlApplicationContext(
				"swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

			EntityManagerFactory emFactory = appCtx.getBean("emFactory", EntityManagerFactory.class);
			final WorkLogFacade workLog = appCtx.getBean("workLog", WorkLogFacade.class);

			System.out.println("----------------- testSaveEmployee -----------------");
			JpaUtil.beginTransaction(emFactory);
			testSaveEmployee(workLog);
			JpaUtil.commitTransaction(emFactory);

			System.out.println("----------------- testAddLogbookEntry -----------------");
			JpaUtil.beginTransaction(emFactory);
			testAddLogbookEntry(workLog);
			JpaUtil.commitTransaction(emFactory);

			System.out.println("----------------- testFindByIds ----------------- ");
			JpaUtil.beginTransaction(emFactory);
			testFindById(workLog);
			JpaUtil.commitTransaction(emFactory);

			System.out.println("----------------- testFindAll -----------------");
			// NOTE: testFindAll method requires an open session to work properly
			JpaUtil.beginTransaction(emFactory);
			testFindAll(workLog);
			JpaUtil.commitTransaction(emFactory);
		}

	}

	private static void testBusinessLogicWithSpringDataRepositories() {
		System.out.println("==================================================================");
		System.out.println("========== testBusinessLogicWithSpringDataRepositories ===========");
		System.out.println("==================================================================");
		
		try (AbstractApplicationContext appCtx = new ClassPathXmlApplicationContext(
				"swt6/spring/worklog/test/applicationContext-jpa2.xml")) {

			EntityManagerFactory emFactory = appCtx.getBean("emFactory", EntityManagerFactory.class);
			final WorkLogFacade workLog = appCtx.getBean("workLog", WorkLogFacade.class);

			System.out.println("----------------- testSaveEmployee -----------------");
			testSaveEmployee(workLog);

			System.out.println("----------------- testAddLogbookEntry -----------------");
			testAddLogbookEntry(workLog);

			System.out.println("----------------- testFindByIds ----------------- ");
			testFindById(workLog);

			System.out.println("----------------- testFindAll -----------------");
			JpaUtil.executeInOpenEntityManager(emFactory, () -> testFindAll(workLog));
		}

	}

	public static void main(String[] args) {

		testBusinessLogicWithJpaDaos();

		//testBusinessLogicWithSpringDataRepositories();

	}
}
