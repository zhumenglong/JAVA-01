package learn.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopByAnnotation {

    //指定切点切面
    @Pointcut(value = "execution(* learn.*.Foo.*sayHelloFoo(..))")
    public void point() {

    }

    @Before(value = "point()")
    public void before() {
        System.out.println("========>before sayHelloFoo");
    }

    @AfterReturning(value = "point()")
    public void after() {
        System.out.println("========>after sayHelloFoo");
    }

    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("========>before sayHelloFoo around");
        joinPoint.proceed();
        System.out.println("========>after sayHelloFoo around");

    }
}
