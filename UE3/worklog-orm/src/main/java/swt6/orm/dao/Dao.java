package swt6.orm.dao;

import java.util.List;

import swt6.orm.domain.EntityBase;

public interface Dao<T extends EntityBase> {
	public Long create(T entity);
	public T update(T entity);
	public void delete(T entity);
	public T get(final Class<? extends T> type, long id);
	public List<T> getAll(final Class<? extends T> type);
}
