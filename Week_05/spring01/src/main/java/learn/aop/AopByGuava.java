package learn.aop;

import com.google.common.reflect.Reflection;
import learn.biz.Foo;
import learn.biz.FooHandler;
import learn.biz.FooInterface;

public class AopByGuava {

    public static void main(String[] args) {

        FooHandler fooHandler = new FooHandler(new Foo());
        FooInterface foo = Reflection.newProxy(FooInterface.class, fooHandler);
        foo.sayHelloFoo();

    }
}


