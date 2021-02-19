package learn;

import learn.beans.School;
import learn.biz.Bar;
import learn.biz.Foo;
import learn.netty.inbound.HttpServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplication {

    public static void main(String[] args) {
        SpringApplication demo = new SpringApplication();
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //demo.homework1(context);
        //demo.homework2(context);
        //demo.homework3(context);
        demo.homeWork4(context);
    }

    /**
     * 使 Java 里的动态代理，实现一个简单的 AOP。
     */
    private void homework1(ApplicationContext context){
        Foo foo = context.getBean(Foo.class);
        foo.sayHelloFoo();
    }

    /**
     * 写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）
     */
    private void homework2(ApplicationContext context){
        Bar bar = context.getBean(Bar.class);
        bar.sayHelloBar();
    }

    private void homework3(ApplicationContext context){
        School school = context.getBean("school", School.class);
        System.out.println(school.toString());
    }

    private void homeWork4(ApplicationContext context){
        HttpServer server = (HttpServer) context.getBean("httpServer");
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
