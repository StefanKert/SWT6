package swt6.spring.basics.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

public class TraceAdviceConfigBased {

	private String getMethodName(JoinPoint jp) {
		return jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
	}

	public void traceBefore(JoinPoint jp) {
		System.out.println("--> " + getMethodName(jp));
	}

	public void traceAfter(JoinPoint jp) {
		System.out.println("<-- " + getMethodName(jp));
	}

	public Object traceAround(ProceedingJoinPoint pjp) throws Throwable {

		long start = System.currentTimeMillis();

		Object retVal = pjp.proceed(); // delegates to method of target class.

		System.out.println("[TRACE] execution of method: " + getMethodName(pjp) + " lasted: "
				+ (System.currentTimeMillis() - start) + "ms.");

		return retVal;
	}

	public void traceException(JoinPoint jp, Throwable exception) {
		System.out.printf("##> %s%n threw exception <%s>%n", getMethodName(jp), exception);
	}

}
