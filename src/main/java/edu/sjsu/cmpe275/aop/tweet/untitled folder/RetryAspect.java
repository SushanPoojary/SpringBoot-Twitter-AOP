package edu.sjsu.cmpe275.aop.tweet.aspect;

import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;

@Aspect
@Order(1)
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     * @throws Throwable 
     */

	// @Around("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.*tweet(..))")
	// public int dummyAdviceOne(ProceedingJoinPoint joinPoint) throws Throwable {
	// 	System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	// 	Integer result = null;
	// 	try {
	// 		result = (Integer) joinPoint.proceed();
	// 		System.out.printf("Finished the executuion of the metohd %s with result %s\n", joinPoint.getSignature().getName(), result);
	// 	} catch (Throwable e) {
	// 		e.printStackTrace();
	// 		System.out.printf("Aborted the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	// 		throw e;
	// 	}
	// 	return result.intValue();
	// }

	int retry = 3;
	
	@Around("execution(* edu.sjsu.cmpe275.aop.tweet.TweetService.*(..))")
	public Object dummyAdviceOne(ProceedingJoinPoint joinPoint) throws Throwable {
		// System.out.println("N/W Prior to the executuion of the metohd " + joinPoint.getSignature().getName());
		int i;
		Throwable except = null;
		for (i = 0; i <= retry; i++) {
			try {
				Object result = joinPoint.proceed();
				return result;
			} catch (IOException e) {
				except = e;
				if (i > 0)
					System.out.println("Retry Connection Attempt: " + i);
				else
					System.out.println("Aborted " + joinPoint.getSignature().getName()
							+ " because of network failure");
				continue;
			}
		}
		if (i == 4 && except != null) {
			System.out.println("Maximum retry attempts exceeded...");
			throw except;
		}
		return null;
	}

}
