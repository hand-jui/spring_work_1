package com.example.demo4.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component // IoC의 관리 대상이 된다.(싱글톤)
public class AopParameter {

//	포인트 컷
//	rule 설정
//	execution 표현식
	@Pointcut("execution(* com.example.demo4.controller..*.*(..))")
	private void cut() {
	}

//	cut() 메서드가 실행되는 지점 이전에 before() 메서드를 실행
	@Before("cut()")
	public void before(JoinPoint joinpoint) {

//		활용 - 어떤 메서드가 동작되었는지 확인
		MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
		Method method = methodSignature.getMethod();

		Object[] args = joinpoint.getArgs();

//		매개 변수 출력
		for (Object obj : args) {
			System.out.println("type : " + obj.getClass().getSimpleName());
			System.out.println("value : " + obj);
		}

		System.out.println("사용된 method 확인");
		System.out.println("method : " + method.getName());

//		controller -> /api/get 호출 하기전에 수행되는 녀석
//		어떤 메서드가 수행되었는지 알아보는 방법
		System.out.println("before 수행!!!");
	}

	@AfterReturning(value = "cut()", returning = "obj")
	public void afterReturn(JoinPoint joinPoint, Object obj) {
		System.out.println("===========");
		System.out.println("return obj");
		System.out.println("obj : " + obj);
	}
}
