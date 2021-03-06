package swt6.orm.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import swt6.orm.dao.Dao;
import swt6.orm.domain.EntityBase;
import swt6.orm.util.HibernateUtil;

public class HibernateDao<T extends EntityBase> implements Dao<T> {

	@Override
	public Long create(T entity) {
		Session session = HibernateUtil.getCurrentSession();

		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
		
		return entity.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T update(T entity) {
		Session session = HibernateUtil.getCurrentSession();

		Transaction tx = session.beginTransaction();
		entity = (T) session.merge(entity);
		tx.commit();

		return entity;
	}

	@Override
	public void delete(T entity) {
		Session session = HibernateUtil.getCurrentSession();

		Transaction tx = session.beginTransaction();
		session.delete(entity);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(final Class<? extends T> type, long id) {
		Session session = HibernateUtil.getCurrentSession();

		Transaction tx = session.beginTransaction();
		T entity = (T) session.get(type, id);
		tx.commit();
		
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(final Class<? extends T> type) {
		Session session = HibernateUtil.getCurrentSession();

		Transaction tx = session.beginTransaction();
		final Criteria crit = session.createCriteria(type);
		List<T> lst = crit.list();

		tx.commit();
		return lst;
	}

}
