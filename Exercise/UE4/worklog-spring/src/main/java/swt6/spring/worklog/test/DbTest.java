package swt6.spring.worklog.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.domain.Employee;
import swt6.util.DateUtil;

public class DbTest {

	private static void createSchema(DataSource ds, String ddlScript) {
		try {
			DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
			InputStream is = DbTest.class.getClassLoader().getResourceAsStream(ddlScript);
			if (is == null)
				throw new IllegalArgumentException(String.format("File %s not found in classpath.", ddlScript));
			scriptRunner.runScript(new InputStreamReader(is));
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return;
		}
	}

	private static void testJdbc() {

		try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
				"swt6/spring/worklog/test/applicationContext-jdbc.xml")) {

			System.out.println("----------------- create schema ----------------- ");
			createSchema(factory.getBean("dataSource", DataSource.class),
					"swt6/spring/worklog/test/CreateWorklogDbSchema.sql");

			//
			// get reference to implementation of EmployeeDao
			//
			EmployeeDao emplDao = factory.getBean("emplDaoJdbc", EmployeeDao.class);

			System.out.println("----------------- save employee ----------------- ");
			Employee empl1 = new Employee("Max", "Muster", DateUtil.getDate(1970, 10, 26));
			emplDao.save(empl1);
			
//TODO enable this after implemented
//			System.out.println("----------------- update employee ----------------- ");
//			empl1.setFirstName("Kevin");
//			empl1 = emplDao.merge(empl1);
//
//			System.out.println("----------------- find employee ----------------- ");
//			Employee empl = emplDao.findById(1L);
//			System.out.println("empl=" + (empl == null ? (null) : empl.toString()));
//
//			empl = emplDao.findById(100L);
//			System.out.println("empl=" + (empl == null ? (null) : empl.toString()));
//
//			System.out.println("----------------- find all employees ----------------- ");
//			for (Employee e : emplDao.findAll())
//				System.out.println(e);

		}
	}

	private static void testJpa() {
//	    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
//	        "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {
//
//	      EntityManagerFactory emFactory = factory.getBean("emFactory", EntityManagerFactory.class);
//
//	      EmployeeDao emplDao = factory.getBean("emplDaoJpa", EmployeeDao.class);
//
//	      System.out.println("----------------- save employee ----------------- ");
//
//	      Employee empl1 = new Employee("John", "Doe", DateUtil.getDate(1950, 1, 1));
//
//	      JpaUtil.beginTransaction(emFactory);
//	      emplDao.save(empl1);
//	      JpaUtil.commitTransaction(emFactory);
//	      
//	      System.out.println("----------------- update employee ----------------- ");
//	      
//	      JpaUtil.beginTransaction(emFactory);
//	      empl1.setFirstName("James");
//	      empl1 = emplDao.merge(empl1);
//	      JpaUtil.commitTransaction(emFactory);
//
//	      System.out.println("----------------- find employee ----------------- ");
//	      
//	      JpaUtil.beginTransaction(emFactory);
//	      Employee empl = emplDao.findById(empl1.getId());
//	      System.out.println("empl=" + (empl == null ? (null) : empl.toString()));
//	      empl = emplDao.findById(100L);
//	      System.out.println("empl=" + (empl == null ? (null) : empl.toString()));
//	      JpaUtil.commitTransaction(emFactory);
//	      
//	      System.out.println("----------------- find all employees ----------------- ");
//
//	      JpaUtil.beginTransaction(emFactory);
//	      System.out.println("findAll");
//	      for (Employee e : emplDao.findAll())
//	        System.out.println(e);
//	      JpaUtil.commitTransaction(emFactory);
//
//	      // ---------------------------- LogbookEntry tests --------------------------
//
//	      LogbookEntryDao entryDao = factory.getBean("entryDaoJpa", LogbookEntryDao.class);
//
//	      System.out.println("----------------- save logbook entries ----------------- ");
//	      
//	      JpaUtil.beginTransaction(emFactory);
//	      LogbookEntry entry1 =
//	          new LogbookEntry("Analyse", DateUtil.getTime(8, 30), DateUtil.getTime(16, 15));
//	      entry1.setEmployee(empl1);
//	      entryDao.save(entry1);
//
//	      LogbookEntry entry2 =
//	          new LogbookEntry("Implementierung", DateUtil.getTime(8, 0), DateUtil.getTime(17, 30));
//	      entry2.setEmployee(new Employee("Valentino", "Hummelbauer", DateUtil.getDate(1940, 12, 24)));
//	      entryDao.save(entry2);
//
//	      JpaUtil.commitTransaction(emFactory);
//
//	      System.out.println("----------------- find all logbook entries ----------------- ");
//	      
//	      JpaUtil.beginTransaction(emFactory);
//	      for (LogbookEntry e : entryDao.findAll())
//	        System.out.println(e);
//	      JpaUtil.commitTransaction(emFactory);
//
//	    }
	  }

	private static void testSpringData() {
	    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
	        "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

//	      EntityManagerFactory emFactory = factory.getBean("emFactory", EntityManagerFactory.class);
//
//	      JpaUtil.openEntityManager(emFactory);
//	      EmployeeRepository emplRepo =
//	          JpaUtil.getRepositoryForCurrentEntityManager(emFactory, EmployeeRepository.class);
//
//	      Employee empl1 = new Employee("John", "Doe", DateUtil.getDate(1950, 1, 1));
//	      Employee empl2 = new Employee("Jane", "Doe", DateUtil.getDate(1940, 5, 3));
//
//	      System.out.println("------------- save / update employee -------------- ");
//
//	      JpaUtil.beginTransaction(emFactory);
//	      empl1 = emplRepo.save(empl1);
//	      empl2 = emplRepo.save(empl2);
//	      emplRepo.flush();	      
//	      empl1.setLastName("Doe-Simpson");
//	      empl1 = emplRepo.save(empl1);
//	      // commit but do not close current EntityManager
//	      JpaUtil.commitTransaction(emFactory, false);
//
//	      System.out.println("--------------------------------------------------");
//
//	      JpaUtil.beginTransaction(emFactory);
//
//	      System.out.println("---------------- findOne ---------------- ");
//	      Employee empl = emplRepo.findOne(1L);
//	      System.out.println("empl=" + (empl == null ? (null) : empl.toString()));
//
//	      System.out.println("---------------- findAll ---------------- ");
//	      for (Employee e : emplRepo.findAll())
//	        System.out.println(e);
//
//	      JpaUtil.commitTransaction(emFactory, false);
	      
	      // ------------------ EmployeeRepository Interface methods start here ----
	      
//	      JpaUtil.beginTransaction(emFactory);
//	      System.out.println("---------------- findByLastName ---------------- ");
//	      empl = emplRepo.findByLastName("Doe");
//	      System.out.println("empl=" + (empl == null ? (null) : empl.toString()));
//	      JpaUtil.commitTransaction(emFactory, false);
//	      
//	      
//	      System.out.println("------------ findByLastNameContaining ---------- ");
//	      
//	      JpaUtil.beginTransaction(emFactory);
//	      for (Employee e : emplRepo.findByLastNameContaining("Simpson"))
//	        System.out.println(e);
//	      JpaUtil.commitTransaction(emFactory, false);
//
//	      System.out.println("---------------- findOlderThan ---------------- ");
//	      
//	      JpaUtil.beginTransaction(emFactory);
//	      for (Employee e : emplRepo.findOlderThan(DateUtil.getDate(1948, 1, 1)))
//	        System.out.println(e);
//	      // now we can close the current EntityManager
//	      JpaUtil.commitTransaction(emFactory, true); 
	    }
	  }


	public static void main(String[] args) {

		System.out.println("===========================================================");
		System.out.println("======================== testJDBC =========================");
		System.out.println("===========================================================");
		testJdbc();

		// System.out.println("===========================================================");
		// System.out.println("========================= testJpa =========================");
		// System.out.println("===========================================================");
		// testJpa();
		//
		// System.out.println("===========================================================");
		// System.out.println("====================== testSpringData =====================");
		// System.out.println("===========================================================");
		// testSpringData();
	}
}
