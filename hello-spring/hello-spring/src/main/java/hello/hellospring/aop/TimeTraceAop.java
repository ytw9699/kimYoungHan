package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Aspect//Aop는 Aspect어노테이션 붙여야 함.
//@Component//이 클래스를 스프링 빈에 등록해 줄 때 @Component를 붙여주면 ConponemtScan으로 등록되고
// spring bean에 직접 등록해줘도 됨.(이것을 더 선호함)
public class TimeTraceAop {
    //@Around("execution(* hello.hellospring..*(..))")
    @Around("execution(* hello.hellospring..*(..)) && !target(hello.hellospring.config.SpringConfig)") //원하는 공통관심사항을 어디에 적용할것인가?
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {//예외터지면 던진다

        long start = System.currentTimeMillis();

        System.out.println("START: " + joinPoint.toString());//어떤 메소드 호출했는지 확인

        try {
//            Object result = joinPoint.proceed();
//            return result;
//            위의 두 줄을 alt + ctrl + N => inline으로 합쳐줌(아래)
              return joinPoint.proceed();//다음 메소드 진행

        } finally {

            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms"); //시간까지 확인
        }
    }
}
