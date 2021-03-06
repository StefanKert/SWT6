package swt6.spring.basics.ioc.util;

import java.util.Properties;

public class LoggerFactory {

	private static String getConfiguredType(String configFile) {

		Properties props = new Properties();

		try {
			ClassLoader cl = Logger.class.getClassLoader();
			props.load(cl.getResourceAsStream(configFile));
		} catch (Exception e) {
			System.err.println("config file not found, using default: console Logger");
		}

		String type = props.getProperty("loggerType", "consoleLogger");
		return type;
	}

	public static Logger getLogger(String configFile) {
		if (LoggerFactory.getConfiguredType(configFile).toLowerCase().equals("file"))
			return new FileLogger("log.txt");
		else if(LoggerFactory.getConfiguredType(configFile).toLowerCase().equals("error"))
			return new ErrorLogger();
		else
			return new ConsoleLogger();
	}
}
