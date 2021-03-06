package swt6.orm.data;

import java.util.List;

import swt6.orm.domain.models.IEntity;

public interface IDataProvider<T extends IEntity> {
	public T get(final Class<? extends T> type, long id);
	public List<T> getAll(final Class<? extends T> type);
	public Long create(T entity);
	public T update(T entity);
	public void delete(T entity);
}