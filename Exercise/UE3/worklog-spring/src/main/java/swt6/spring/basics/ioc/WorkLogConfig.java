package swt6.spring.basics.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import swt6.spring.basics.ioc.logic.WorkLogFacade;
import swt6.spring.basics.ioc.logic.WorkLogImplJavaBased;
import swt6.spring.basics.ioc.util.ConsoleLogger;
import swt6.spring.basics.ioc.util.Logger;

@Configuration
public class WorkLogConfig {

	@Bean
	public Logger logger() {
		return new ConsoleLogger();
	}
	
	@Bean
	public WorkLogFacade workLog() {
		return new WorkLogImplJavaBased(logger());
	}
}
