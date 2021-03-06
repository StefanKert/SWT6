package swt6.orm.data.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import swt6.orm.data.IDataProvider;
import swt6.orm.domain.models.IEntity;

public class HibernateDataProvider<T extends IEntity>  implements IDataProvider<T> {

	@Override
	public Long create(T entity) {
		return HibernateUtil.executeWithTransactionWithResult((session) -> {
			session.save(entity);
			return entity.getId();
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public T update(T entity) {
		return HibernateUtil.executeWithTransactionWithResult((session) -> {
			return (T)session.merge(entity);
		});
	}

	@Override
	public void delete(T entity) {
		HibernateUtil.executeWithTransaction((session) -> {
			session.delete(entity);
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(final Class<? extends T> type, long id) {
		return HibernateUtil.executeWithTransactionWithResult((session) -> {
			return (T) session.get(type, id);
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(final Class<? extends T> type) {
		return HibernateUtil.executeWithTransactionWithResult((session) -> {
			final Criteria crit = session.createCriteria(type);
			return crit.list();
		});
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllByRestriction(Class<? extends T> type, Criterion restriction) {
		return HibernateUtil.executeWithTransactionWithResult((session) -> {
			final Criteria crit = session.createCriteria(type);
			crit.add(restriction);
			return crit.list();
		});
	}
}
