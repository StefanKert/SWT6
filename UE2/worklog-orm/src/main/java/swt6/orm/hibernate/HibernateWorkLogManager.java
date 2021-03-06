package swt6.orm.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import swt6.orm.data.hibernate.HibernateUtil;
import swt6.orm.domain.models.Address;
import swt6.orm.domain.models.Employee;
import swt6.orm.domain.models.LogbookEntry;
import swt6.orm.domain.models.PermanentEmployee;
import swt6.orm.domain.models.Project;
import swt6.orm.domain.models.TemporaryEmployee;
import swt6.util.DateUtil;

public class HibernateWorkLogManager {

	// version 2
	private static <T> void saveEntity(T entity) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(entity);
		tx.commit(); // session is closed automatically
	}

	@SuppressWarnings("unchecked")
	private static void listEmployees() {
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();

		List<Employee> emplList = session.createQuery("select e from Employee e").list();
		for (Employee e : emplList) {
			System.out.println(e);
			if (e.getLogbookEntries().size() > 0) {
				System.out.println("  logbookEntries:");
				for (LogbookEntry lbe : e.getLogbookEntries())
					System.out.println("    " + lbe.getActivity());
			}
		}

		tx.commit();
	}

	private static Employee updateEmployee(Employee empl) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.get(Employee.class, empl.getId());
		empl = (Employee) session.merge(empl);
		tx.commit();
		return empl;
	}

	private static Employee addLogbookEntries(Employee empl, LogbookEntry... entries) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();

		// variant 2:
		empl = (Employee) session.merge(empl);

		for (LogbookEntry entry : entries) {
			empl.addLogbookEntry(entry); // or entry.attachEmployee(empl);
		}

		// variant 1:
		// session.saveOrUpdate(empl);
		tx.commit();
		return empl;
	}

	private static Employee getEmployeeById(long emplId) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Employee empl = (Employee) session.get(Employee.class, emplId);
		// If load() can’t find the object in the cache or database, an
		// exception is thrown.
		// The load() method never returns null
		// Employee empl = (Employee) session.load(Employee.class, emplId);
		tx.commit();
		return empl;
	}

	private static LogbookEntry getLogbookEntryById(long leId) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();
		LogbookEntry le = (LogbookEntry) session.get(LogbookEntry.class, leId);
		tx.commit();
		return le;
	}

	@SuppressWarnings("unchecked")
	private static void listLogbookEntriesOfEmployee(Long emplId) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();

		System.out.println("logbook entries of employee id: " + emplId);

		// Keep in mind: HQL queries refer to Java (domain) objects not to
		// database tables.

		// Version 1: Concatenate SQL query string manually
		// Query qry = session.createQuery("from LogbookEntry where employee.id
		// = " + emplId);
		// Iterator<LogbookEntry> it = qry.iterate();

		// Version 2: Use named SQL parameters
		// Query qry =
		// session.createQuery("from LogbookEntry where employee.id=:emplId");
		// qry.setLong("emplId", emplId);
		// Iterator<LogbookEntry> it = qry.iterate();

		// Version 3: simply get the Employee and iterate over its objects
		// Employee empl = (Employee) session.get(Employee.class, emplId);
		// Iterator<LogbookEntry> it = empl.getLogbookEntries().iterator();

		// Version 4: Use Hibernate criteria API
		Criteria crit = session.createCriteria(LogbookEntry.class);
		crit.add(Restrictions.eq("employee.id", emplId));
		Iterator<LogbookEntry> it = crit.list().iterator();

		while (it.hasNext())
			System.out.println(it.next());

		tx.commit();

	}

	private static void testFetchingStrategies() {

		System.out.println("----- testFetchingStrategies ------");

		// prepare data
		LogbookEntry entry1 = new LogbookEntry("Analyse", DateUtil.getTime(10, 15), DateUtil.getTime(15, 30));
		//saveEntity(entry1);
		Employee max = new Employee("Max", "Muster", DateUtil.getDate(2015, 03, 28));
		max.addLogbookEntry(entry1);
		saveEntity(max);

		Long entryId = entry1.getId();
		Long emplId = max.getId();

		System.out.println("############################################");

		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();

		System.out.println("###> Fetching LogbookEntry ...");
		LogbookEntry entry = (LogbookEntry) session.get(LogbookEntry.class, entryId);
		System.out.println("###> Fetched LogbookEntry");
		Employee empl1 = entry.getEmployee();
		System.out.println("###> Fetched associated Employee");
		System.out.println(empl1);
		System.out.println("###> Accessed associated Employee");

		tx.commit();

		System.out.println("############################################");

		session = HibernateUtil.getCurrentSession();
		tx = session.beginTransaction();

		System.out.println("###> Fetching Employee ...");
		Employee empl2 = (Employee) session.get(Employee.class, emplId);
		System.out.println("###> Fetched Employee");
		Set<LogbookEntry> entries = empl2.getLogbookEntries();
		System.out.println("###> Fetched associated entries");
		for (LogbookEntry e : entries)
			System.out.println("  " + e);
		System.out.println("###> Accessed associated entries");

		tx.commit();

		System.out.println("############################################");

		System.out.println("->no lazy initialization exception when accessed within valid session");

		session = HibernateUtil.getCurrentSession();
		tx = session.beginTransaction();
		Employee e1 = (Employee) session.get(Employee.class, emplId);
		System.out.println("logbook entry for max: " + e1.getLogbookEntries().iterator().next());
		tx.commit();

		System.out.println("->lazy initialization exception when lazy, without session or not eager joined");

		session = HibernateUtil.getCurrentSession();
		tx = session.beginTransaction();
		Employee e2 = (Employee) session.get(Employee.class, emplId);
		tx.commit();
		System.out.println("logbook entry for max: " + e2.getLogbookEntries().iterator().next());
	}


	
	public static void testInheritance() {
		PermanentEmployee pe = new PermanentEmployee("Franz", "Mayr",
				DateUtil.getDate(1980, 12, 24));
		pe.setAddress(new Address("4232", "Hagenberg", "Hauptstraße 1"));
		pe.setSalary(5000.0);
		saveEntity(pe);

		TemporaryEmployee te = new TemporaryEmployee("Bill", "Gates",
				DateUtil.getDate(1970, 1, 21));
		te.setAddress(new Address("77777", "Redmond", "Clinton Street"));
		te.setHourlyRate(50.0);
		te.setRenter("Microsoft");
		te.setStartDate(DateUtil.getDate(2006, 3, 1));
		te.setEndDate(DateUtil.getDate(2006, 4, 1));
		saveEntity(te);

		listEmployees();
	}
	
	public static void testEmployeeWithAddress() {
		System.out.println("----- test EmployeeWithAddress -----");
		Address address = new Address("4020", "Linz", "Hauptplatz 1");
		Employee max = new Employee("Max", "Muster", DateUtil.getDate(2015, 03, 28), address);
		address.setEmployee(max);
		saveEntity(max);
		System.out.println(getEmployeeById(max.getId()));
		System.out.println("----- DONE test EmployeeWithAddress -----");
	}

	private static void testProjectEmployees() {

		System.out.println("----- test testProjectEmployees -----");

		Employee john = new Employee("John", "Doe_Project", DateUtil.getDate(2015, 03, 28));
		Employee jane = new Employee("Jane", "Doe_Project", DateUtil.getDate(2015, 03, 28));

		john.setAddress(new Address("4232", "Hagenberg", "Hauptstraße 1"));
		jane.setAddress(new Address("4232", "Hagenberg", "Hauptstraße 1"));

		Project p1 = new Project("Office");
		Project p2 = new Project("Enterprise Server");

		john.addProject(p1);
		john.addProject(p2);
		jane.addProject(p2);

		// we used for the connection between employee and project
		// cascade="save-update"
		// therefore saving the employee is sufficient for saving the projects
		// in the db
		saveEntity(john);
		saveEntity(jane);

		listEmployees();
		System.out.println("----- DONE test testProjectEmployees -----");
	}
	
	
	public static void main(String[] args) {
		try {
			System.out.println("----- create schema -----");
			HibernateUtil.getSessionFactory();
			
			Employee john = new Employee("John", "Doe", DateUtil.getDate(2015, 03, 28));
			Employee jane = new Employee("Jane", "Doe", DateUtil.getDate(2015, 03, 28));

			john.setAddress(new Address("4020", "Linz", "John-Straße-1"));
			jane.setAddress(new Address("4020", "Linz", "Jane-Straße-1"));
			
			
			
			LogbookEntry entry1 = new LogbookEntry("Analyse", DateUtil.getTime(10, 15), DateUtil.getTime(15, 30));
			LogbookEntry entry2 = new LogbookEntry("Implementierung", DateUtil.getTime(8, 45),
					DateUtil.getTime(17, 15));
			LogbookEntry entry3 = new LogbookEntry("Testen", DateUtil.getTime(12, 30), DateUtil.getTime(17, 00));

			System.out.println("----- saveEmployees -----");
			saveEntity(john);
			saveEntity(jane);

			System.out.println("----- addLogbookEntries -----");
			john = addLogbookEntries(john, entry1, entry2);
			jane = addLogbookEntries(jane, entry3);

			System.out.println("----- listEmployees -----");
			listEmployees();

			System.out.println("----- getEmployee -----");
			System.out.println(getEmployeeById(jane.getId()));

			System.out.println("----- getLogbookEntry -----");
			LogbookEntry e = getLogbookEntryById(entry1.getId());
			System.out.print("getLogbookEntry id is: "+e.getId() +" ");
			//the following line will fail unless we set the LogbookEntry.hbm.xml lazy="false" or fetch="join"
			System.out.println(e.toString()); 
			
//			System.out.println("\n----- listLogbookEntriesOfEmployee -----");
//			listLogbookEntriesOfEmployee(john.getId());

//			System.out.println("----- testFetchingStrategies ------");
//			testFetchingStrategies();
			
//			testEmployeeWithAddress();
			
//			System.out.println("----- testInheritance ------");
			testInheritance();
			
		} finally {
			HibernateUtil.closeSessionFactory();
		}
	}
}