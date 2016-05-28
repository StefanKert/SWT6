package swt6.spring.basics.hello;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GreetingClient {

	public static void main(String[] args) {
		try(AbstractApplicationContext context = new ClassPathXmlApplicationContext("swt6/spring/basics/hello/greetingService.xml")) {
			GreetingService bean = context.getBean("greetingService", GreetingService.class);
			bean.sayHello();
		}
	}

}
