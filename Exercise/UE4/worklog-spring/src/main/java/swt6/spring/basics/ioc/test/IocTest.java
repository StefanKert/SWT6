package swt6.spring.basics.ioc.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import swt6.spring.basics.ioc.WorkLogConfig;
import swt6.spring.basics.ioc.logic.WorkLogFacade;
import swt6.spring.basics.ioc.logic.WorkLogImplFactoryBased;

public class IocTest {

	private static void testSimple() {
		WorkLogImplFactoryBased workLog = new WorkLogImplFactoryBased();
		workLog.findAllEmployees();
		workLog.findEmployeeById(3L);
	}

	// TODO Version 1
	private static void testConfigBasedIoC() {
		try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
				"swt6/spring/basics/ioc/test/applicationContext-xml-config.xml")) {

			System.out.println("***> workLog-setter-injected:");
			WorkLogFacade workLog1 = factory.getBean("workLog-setter-injected", WorkLogFacade.class);
			workLog1.findAllEmployees();
			workLog1.findEmployeeById(3L);

			System.out.println("***> workLog-constructor-injected:");
			WorkLogFacade workLog2 = factory.getBean("workLog-constructor-injected", WorkLogFacade.class);
			workLog2.findAllEmployees();

		}
	}

	// TODO Version 2
	private static void testAnnotationBasedIoC() {
		try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
				"swt6/spring/basics/ioc/test/applicationContext-annotation-config.xml")) {

			WorkLogFacade workLog = factory.getBean("workLog", WorkLogFacade.class);
			workLog.findAllEmployees();
			workLog.findEmployeeById(3L);
			workLog.findEmployeeById(99L);
		}
	}

	// TODO Version 3
	private static void testJavaConfig() {
		try (AbstractApplicationContext factory = new AnnotationConfigApplicationContext(WorkLogConfig.class)) {
			WorkLogFacade workLog = factory.getBean(WorkLogFacade.class);

			workLog.findAllEmployees();
			workLog.findEmployeeById(3L);
		}
	}

	public static void main(String[] args) {
		System.out.println("==================== testSimple ======================");
		testSimple();

		// TODO Version 1
		System.out.println("================= testConfigBasedIoC =================");
		testConfigBasedIoC();

		// TODO Version 2
		System.out.println("================ testAnnotationBasedIoC ===============");
		testAnnotationBasedIoC();

		 //TODO Version 3
		 System.out.println("================ testJavaConfig ===============");
		 testJavaConfig();
	}
}
