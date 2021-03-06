package swt6.orm.hibernate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.Transaction;

import swt6.orm.data.hibernate.HibernateUtil;
import swt6.orm.domain.models.Employee;

public class HibernateEmployeeManager {

	static String promptFor(BufferedReader in, String p) {
		System.out.print(p + "> ");
		try {
			return in.readLine();
		} catch (Exception e) {
			return promptFor(in, p);
		}
	}
	
	public static void saveEmployee(Employee empl) {
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(empl);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	private static Collection<Employee> listEmployees() {
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Collection<Employee> result = session.createQuery("select e from Employee e").list();
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
					for (Employee empl : listEmployees())
						System.out.println(empl);
					break;

				case "save":
					try {
						saveEmployee(new Employee(promptFor(in, "firstName"), promptFor(in, "lastName"),
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
