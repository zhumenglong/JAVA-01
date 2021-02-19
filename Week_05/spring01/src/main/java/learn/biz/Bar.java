package learn.biz;

import org.springframework.stereotype.Component;

@Component
public class Bar {

    public String sayHelloBar() {
        System.out.println("Hello in Bar!");
        return "Hello in Bar!";
    }

}
