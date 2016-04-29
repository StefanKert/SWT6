package swt6.orm.dao;

public abstract class DaoHolder {
	
	protected static DaoHolder instance;
	
	protected DaoHolder(){}
	
	public abstract DaoHolder getInstance();
	
	
}
