package swt6.orm.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import swt6.orm.dao.Dao;
import swt6.orm.domain.EntityBase;
import swt6.orm.jpa.JPAUtil;

public class JpaDao<T extends EntityBase> implements Dao<T> {

	@Override
	public Long create(T entity) {
		EntityManager em = JPAUtil.getTransactedEntityManager();
		em.persist(entity);
		JPAUtil.commit();
		
		return entity.getId();		
	}

	@Override
	public T update(T entity) {
		EntityManager em = JPAUtil.getTransactedEntityManager();
		entity = em.merge(entity);
		JPAUtil.commit();		
		
		return entity;
	}

	@Override
	public void delete(T entity) {
		EntityManager em = JPAUtil.getTransactedEntityManager();		
		em.remove(em.contains(entity) ? entity : em.merge(entity));
		JPAUtil.commit();
	}

	@Override
	public T get(Class<? extends T> type, long id) {
		EntityManager em = JPAUtil.getTransactedEntityManager();		
		T entity = em.find(type, id);
		JPAUtil.commit();
		
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(Class<? extends T> type) {
		EntityManager em = JPAUtil.getTransactedEntityManager();
		List<T> lst = em.createQuery("from " + type.getSimpleName()).getResultList();
		JPAUtil.commit();
		
		return lst;
	}

}
