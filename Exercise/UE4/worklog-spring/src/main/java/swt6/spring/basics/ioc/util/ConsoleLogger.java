package swt6.spring.basics.ioc.util;

import org.springframework.stereotype.Component;

@Component
//@Log(LoggerType.STANDARD)
public class ConsoleLogger implements Logger {

	private String prefix = "Log";

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void log(String msg) {
		System.out.println(prefix + " " + msg);
	}
}
