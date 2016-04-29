package swt6.orm.data.jpa;

import java.util.List;

import javax.persistence.Query;

import swt6.orm.data.IDataProvider;
import swt6.orm.domain.models.IEntity;

public class JPADataProvider<T extends IEntity> implements IDataProvider<T> {

	@Override
	public Long create(T entity) {
		return JPAUtil.executeWithTransactionWithResult((em) -> {
			em.persist(entity);
			return entity.getId();
		});
	}

	@Override
	public T update(T entity) {
		return JPAUtil.executeWithTransactionWithResult((em) -> {
			return  em.merge(entity);
		});
	}

	@Override
	public void delete(T entity) {
		JPAUtil.executeWithTransaction((em) -> {
			em.remove(em.contains(entity) ? entity : em.merge(entity));
		});
	}

	@Override
	public T get(Class<? extends T> type, long id) {
		return JPAUtil.executeWithTransactionWithResult((em) -> {
			return  em.find(type, id);
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(Class<? extends T> type) {
		return JPAUtil.executeWithTransactionWithResult((em) -> {
			return em.createQuery("from " + type.getSimpleName()).getResultList();
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAllByRestriction(Class<? extends T> type, String criteria) {
		return JPAUtil.executeWithTransactionWithResult((em) -> {
			Query query = em.createQuery(criteria);
			return query.getResultList();
		});
	}
}
