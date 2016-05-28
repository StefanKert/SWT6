package swt6.orm.jpa;

import java.text.DateFormat;
import java.util.List;

import javax.persistence.*;

import swt6.orm.domain.Address;
import swt6.orm.domain.Employee;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Project;
import swt6.orm.domain.jpa.JpaAddress;
import swt6.orm.domain.jpa.JpaEmployee;
import swt6.orm.domain.jpa.JpaLogbookEntry;
import swt6.orm.domain.jpa.JpaPermanentEmployee;
import swt6.orm.domain.jpa.JpaProject;
import swt6.orm.domain.jpa.JpaTemporaryEmployee;
import swt6.util.DateUtil;

public class JPAWorkLogManager {
	private static final DateFormat fmt = DateFormat.getDateTimeInstance();

	private static Long saveEmployee(JpaEmployee empl) {
		EntityManager em = JPAUtil.getTransactedEntityManager();
		em.persist(empl);
		JPAUtil.commit();

		return empl.getId();
	}

	private static void getEmployee(long emplId) {
		EntityManager em = JPAUtil.getTransactedEntityManager();
		Employee empl = em.find(JpaEmployee.class, emplId);

		if (empl != null) {
			System.out.println(empl.toString());
			for (LogbookEntry lbe : empl.getLogbookEntries()) {
				System.out.println("  " + lbe.getActivity());
			}
		}

		JPAUtil.commit();
	}

	private static void listEmployees() {
		EntityManager em = JPAUtil.getTransactedEntityManager();

		List<JpaEmployee> emplList = em.createQuery("select e from JpaEmployee e", JpaEmployee.class).getResultList();
		for (Employee e : emplList) {
			System.out.println(e);
			if (e.getLogbookEntries().size() > 0) {
				System.out.println("  logbookEntries:");
				for (LogbookEntry lbe : e.getLogbookEntries())
					System.out
							.println("    " + lbe.getActivity() + ": " + lbe.getStartTime() + " - " + lbe.getEndTime());
			}
			if (e.getProjects().size() > 0) {
				System.out.println("  projects:");
				for (Project p : e.getProjects())
					System.out.println("    " + p.toString());
			}
		}

		JPAUtil.commit();
	}

	private static void addLogbookEntries(JpaEmployee empl) {
		EntityManager em = JPAUtil.getTransactedEntityManager();

//		Project p1 = new JpaProject("Project 1");
//		empl.addLogbookEntry(new JpaLogbookEntry("Implementierung", DateUtil.getTime(8, 45), DateUtil.getTime(17, 15),
//				new JpaPhase("Pending"), new JpaModule("Module 1")));
//		empl.addLogbookEntry(new JpaLogbookEntry("Testen", DateUtil.getTime(12, 30), DateUtil.getTime(17, 00),
//				new JpaPhase("Pending"), new JpaModule("Module 1")));

		empl = em.merge(empl); // necessary to update in the db

		JPAUtil.commit();
	}

	private static void getLogbookEntry(long entryId) {
		EntityManager em = JPAUtil.getTransactedEntityManager();
		LogbookEntry entry = (LogbookEntry) em.find(JpaLogbookEntry.class, entryId);

		if (entry != null) {
			System.out.println("LogbookEntry: " + entry.toString());
		}
		JPAUtil.commit();
	}

	private static void assignProjectsToEmployees(JpaEmployee empl1, JpaEmployee empl2) {
		EntityManager em = JPAUtil.getTransactedEntityManager();
		empl1 = em.merge(empl1);
		empl2 = em.merge(empl2);
		JpaProject p1 = new JpaProject("Office");
		JpaProject p2 = new JpaProject("Enterprise Server");
		empl1.addProject(p1);
		p1.addMember(empl1);
		empl1.addProject(p2);
		p2.addMember(empl2);
		System.out.println(empl1);
		System.out.println("  projects:");
		for (Project p : empl1.getProjects())
			System.out.println("    " + p.toString());
		System.out.println(empl2);
		System.out.println("  projects:");
		for (Project p : empl2.getProjects())
			System.out.println("    " + p.toString());

		JPAUtil.commit();
	}

	public static void listEmployeesOfProject(Long projectId) {
		EntityManager em = JPAUtil.getTransactedEntityManager();

		Project proj = em.find(JpaProject.class, projectId);
		if (proj == null) {
			System.out.format("Project with id <%d> not found.%n", projectId);
			return;
		}

		Query qry = em.createQuery("select e from JpaEmployee e where :proj member of e.projects");

		qry.setParameter("proj", proj);
		List<JpaEmployee> empls = qry.getResultList();

		System.out.format("employees of project <%s>%n", proj);
		for (Employee empl : empls)
			System.out.println("  " + empl);

		JPAUtil.commit();
	}

	public static void testEmployeeWithAddress() {
		System.out.println("----- test EmployeeWithAddress -----");
		Address address = new JpaAddress("4020", "Linz", "Hauptplatz 1");
		JpaEmployee max = new JpaEmployee("Max", "Muster", DateUtil.getDate(2015, 03, 28), address);
		saveEmployee(max);
		listEmployees();
		System.out.println("----- DONE test EmployeeWithAddress -----");
	}

	public static void testInheritance() {
		JpaPermanentEmployee pe = new JpaPermanentEmployee("Franz", "Mayr", DateUtil.getDate(1980, 12, 24));
		pe.setAddress(new JpaAddress("4232", "Hagenberg", "Hauptstraße 1"));
		pe.setSalary(5000.0);
		saveEmployee(pe);

		JpaTemporaryEmployee te = new JpaTemporaryEmployee("Bill", "Gates", DateUtil.getDate(1970, 1, 21));
		te.setAddress(new JpaAddress("77777", "Redmond", "Clinton Street"));
		te.setHourlyRate(50.0);
		te.setRenter("Microsoft");
		te.setStartDate(DateUtil.getDate(2006, 3, 1));
		te.setEndDate(DateUtil.getDate(2006, 4, 1));
		saveEmployee(te);
		JpaEmployee max = new JpaEmployee("Max", "Muster", DateUtil.getDate(2015, 03, 28),
				new JpaAddress("4020", "Linz", "Hauptplatz 1"));
		saveEmployee(max);
		listEmployees();
	}

	public static void main(String[] args) {
		
		System.out.println("----- create schema -----");
		JPAUtil.getEntityManager();

		JpaEmployee empl1 = new JpaEmployee("Franz", "Mayr", DateUtil.getDate(1980, 12, 24));
		empl1.setAddress(new JpaAddress("4232", "Hagenberg", "Hauptstrasse 1"));

		JpaEmployee empl2 = new JpaEmployee("Bill", "Gates", DateUtil.getDate(1970, 1, 21));
		empl2.setAddress(new JpaAddress("77777", "Redmond", "Clinton Street"));

		System.out.println("----- saveEmployee -----");
		saveEmployee(empl1);

		System.out.println("----- saveEmployee -----");
		saveEmployee(empl2);

		System.out.println("----- getEmployee -----");
		getEmployee(empl2.getId());

		System.out.println("----- addLogbookEntries -----");
		addLogbookEntries(empl1);

		System.out.println("----- listEmployees -----");
		listEmployees();

		System.out.println("----- getLogbookEntry -----");
		getLogbookEntry(1L);

		testEmployeeWithAddress();

		System.out.println("----- assignProjectsToEmployees -----");
		assignProjectsToEmployees(empl1, empl2);

		System.out.println("----- listEmployeesOfProject -----");
		listEmployeesOfProject(32768L);
		listEmployeesOfProject(32769L);

		System.out.println("----- testInheritance -----");
		testInheritance();

		System.out.println("----- listEmployees -----");
		listEmployees();

		JPAUtil.closeEntityManagerFactory();
	}

}
