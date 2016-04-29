package swt6.orm.hibernate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import swt6.orm.domain.hibernate.HibernateEmployee;
import swt6.orm.util.HibernateHelper;
import swt6.orm.util.HibernateUtil;

public class HibernateEmployeeManager {

	static String promptFor(BufferedReader in, String p) {
		System.out.print(p + "> ");
		try {
			return in.readLine();
		} catch (Exception e) {
			return promptFor(in, p);
		}
	}

	// Old version
//	public static void saveEmployee(Employee empl) {
//		SessionFactory sessionFactory = HibernateHelper.buildSessionFactory("hibernate.cfg.xml");
//		Session session = sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
//		session.save(empl);
//		tx.commit();
//		session.close();
//		sessionFactory.close();
//	}
	
	public static void saveEmployee(HibernateEmployee empl) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(empl);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	private static Collection<HibernateEmployee> listEmployees() {
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Collection<HibernateEmployee> result = session.createQuery("select e from HibernateEmployee e").list();
		tx.commit();
		return result;
	}

	public static void main(String[] args) {
		DateFormat dfmt = new SimpleDateFormat("dd.MM.yyyy");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String availCmds = "commands: save, list";

		System.out.println("Hibernate Employee Admin");
		System.out.println(availCmds);
		String userCmd = promptFor(in, "");

		while (!userCmd.equals("quit")) {

			try {
				switch (userCmd) {
				case "list":
					for (HibernateEmployee empl : listEmployees())
						System.out.println(empl);
					break;

				case "save":
					try {
						saveEmployee(new HibernateEmployee(promptFor(in, "firstName"), promptFor(in, "lastName"),
								dfmt.parse(promptFor(in, "dob (dd.mm.yyyy)"))));
					} catch (ParseException e) {
						System.err.println("Invalid date format.");
					}

					break;

				default:
					System.out.println("ERROR: invalid command");
					break;
				} // switch
			} // try
			catch (Exception ex) {
				ex.printStackTrace();
			} // catch

			System.out.println(availCmds);
			userCmd = promptFor(in, "");
		} // while

		// TODO later HibernateUtil.closeSessionFactory();
	}
}
