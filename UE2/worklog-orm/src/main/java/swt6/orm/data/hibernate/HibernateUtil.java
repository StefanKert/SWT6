package swt6.orm.data.hibernate;

import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory buildSessionFactory() {
		return buildSessionFactory("hibernate.cfg.xml");
	}

	@SuppressWarnings("serial")
	public static SessionFactory buildSessionFactory(String configFile) {
		Configuration configuration = new Configuration().configure(configFile);

		final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		configuration.setSessionFactoryObserver(new SessionFactoryObserver() {
			@Override
			public void sessionFactoryCreated(SessionFactory factory) {
			}

			@Override
			public void sessionFactoryClosed(SessionFactory factory) {
				StandardServiceRegistryBuilder.destroy(serviceRegistry);
			}
		});

		return configuration.buildSessionFactory(serviceRegistry);
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = buildSessionFactory();
		return sessionFactory;
	}

	public static void closeSessionFactory() {
		if (sessionFactory != null) {
			sessionFactory.close();
			sessionFactory = null;
		}
	}
	
	public static void executeWithTransaction(Consumer<Session> executeMethod){
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();
		executeMethod.accept(session);
		tx.commit();
	}
	
	public static <TResult> TResult executeWithTransactionWithResult(Function<Session, TResult> executeMethod){
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();
		TResult result = executeMethod.apply(session);
		tx.commit();
		return result;
	}
	
	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}
}
