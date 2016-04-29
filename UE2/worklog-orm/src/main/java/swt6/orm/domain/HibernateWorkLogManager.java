package swt6.orm.domain;

import swt6.orm.data.IDataProvider;
import swt6.orm.data.hibernate.HibernateDataProvider;
import swt6.orm.domain.models.IEntity;

public class HibernateWorkLogManager extends WorkLogManager  {
	protected <T extends IEntity> IDataProvider<T> getDataProviderForType(){
		return new HibernateDataProvider<T>();
	}
}