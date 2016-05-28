package swt6.spring.basics.hello;

public class GreeetingServiceImpl implements GreetingService {
	
	private String message;
	
	/* (non-Javadoc)
	 * @see swt6.spring.basics.hello.GreetingInterface#setMessage(java.lang.String)
	 */
	public void setMessage(String message){
		this.message = message;
	}
	
	public void sayHello(){
		System.out.println(message);
	}
	
}
