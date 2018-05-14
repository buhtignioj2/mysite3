package com.cafe24.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExcutionTimeAspect {

    @Around( "execution(* *..repository.*.*(..)) || execution(* *..service.*.*(..))")
    public Object roundAdvice( ProceedingJoinPoint pjp ) throws Throwable {
	// before advice code
	StopWatch sw = new StopWatch();
	sw.start();
	///////////////////////////////
	
	Object result = pjp.proceed();
	
	//////////////////////////////
	
	// after advice code
	sw.stop();
	Long totalTime = sw.getLastTaskTimeMillis();
	
	String className = pjp.getTarget().getClass().getName();
	String methodName = pjp.getSignature().getName();
	String taskName = className + "." + methodName;
	
	System.out.println( "[ExcutionTime]" + "[" + taskName + "]" + totalTime + " millis");
	
	return result;
    }
}
