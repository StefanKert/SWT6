package swt6.orm.domain;

import swt6.orm.data.IDataProvider;
import swt6.orm.data.jpa.JPADataProvider;
import swt6.orm.domain.models.IEntity;

public class JPAWorkLogManager extends WorkLogManager  {
	protected <T extends IEntity> IDataProvider<T> getDataProviderForType(){
		return new JPADataProvider<T>();
	}
}