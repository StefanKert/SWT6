package swt6.spring.basics.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import swt6.spring.basics.ioc.logic.WorkLogFacade;
import swt6.spring.basics.ioc.logic.WorkLogJavaBased;
import swt6.spring.basics.ioc.util.ConsoleLogger;
import swt6.spring.basics.ioc.util.Logger;

@Configuration
@ComponentScan
public class WorkLogConfig {
	@Bean
	public Logger consoleLogger(){
		return new ConsoleLogger();
	}
	
	@Bean
	public WorkLogFacade workLog(){
		return new WorkLogJavaBased(consoleLogger());
	}
}
