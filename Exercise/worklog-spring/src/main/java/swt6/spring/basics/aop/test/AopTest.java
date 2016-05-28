package swt6.spring.basics.aop.test;

import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import swt6.spring.basics.aop.logic.Employee;
import swt6.spring.basics.aop.logic.EmployeeIdNotFoundException;
import swt6.spring.basics.aop.logic.WorkLogFacade;

public class AopTest {

	private static void testAOP(String configFileName) {
		try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(configFileName)) {

			WorkLogFacade workLog = factory.getBean("workLog", WorkLogFacade.class);

			// TODO enable me for to demo which class is really here during
			// runtime
			// reflectClass(workLog.getClass());

			List<Employee> all = workLog.findAllEmployees();

			System.out.println("[MAIN] findAllEmployees returned " + all.size() + " employees.");
			for (int i = 2; i <= all.size() + 1; i++) {
				try {
					Employee e = workLog.findEmployeeById(Long.valueOf(i));
					System.out.println("[MAIN] Employee " + e.getLastName() + " found for ID " + i);
				} catch (EmployeeIdNotFoundException e) {
					System.err.println("[MAIN] Employee not found for ID " + i);
				}
			}

		}
	}

	public static void main(String[] args) {
		System.out.println("=============== testAOP (config based) ===============");
		testAOP("swt6/spring/basics/aop/test/applicationContext-config-based-aop.xml");

		// System.out
		// .println("============= testAOP (annotation based) =============");
		// testAOP("swt6/spring/basics/aop/test/applicationContext-annotation-based-aop.xml");
		System.out.println("DONE");
	}

	public static void reflectClass(Class<?> clazz) {
		System.out.println("class=" + clazz.getName());
		Class<?>[] interfaces = clazz.getInterfaces();
		for (Class<?> itf : interfaces)
			System.out.println("  implements " + itf.getName());
	}
}
