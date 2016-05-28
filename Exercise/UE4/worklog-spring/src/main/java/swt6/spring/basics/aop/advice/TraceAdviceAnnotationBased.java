package swt6.spring.basics.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TraceAdviceAnnotationBased {

	private String getMethodName(JoinPoint jp) {
		return jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
	}

	@Pointcut("execution(public * swt6.spring.basics.aop.logic..*findAll*(..))")
	private void findMethodsPointcut() {
		
	}
	
	// Version 1
	//@Before("execution(public * swt6.spring.basics.aop.logic..*find*(..))")
	@Before("swt6.spring.basics.aop.advice.TraceAdviceAnnotationBased.findMethodsPointcut()")
	public void traceBefore(JoinPoint jp) {
		System.out.println("--> " + getMethodName(jp));
	}

	// Version 1
	//@After("execution(public * swt6.spring.basics.aop.logic..*find*(..))")
	@After("swt6.spring.basics.aop.advice.TraceAdviceAnnotationBased.findMethodsPointcut()")
	public void traceAfter(JoinPoint jp) {
		System.out.println("<-- " + getMethodName(jp));
	}

	@Around("execution(public * swt6.spring.basics.aop.logic..*findAll*(..))")
	public Object traceAround(ProceedingJoinPoint pjp) throws Throwable {

		long start = System.currentTimeMillis();

		Object retVal = pjp.proceed(); // delegates to method of target class.

		System.out.println("[TRACE] execution of method: " + getMethodName(pjp) + " lasted: "
				+ (System.currentTimeMillis() - start) + "ms.");

		return retVal;
	}

	@AfterThrowing(throwing="exception", pointcut="execution(public * swt6.spring.basics.aop.logic..*find*(..))")
	public void traceException(JoinPoint jp, Throwable exception) {
		System.out.printf("##> %s%n threw exception <%s>%n", getMethodName(jp), exception);
	}

}
