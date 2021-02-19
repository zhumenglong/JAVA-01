package learn.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.Callable;

public class Monitor {

    @RuntimeType
    public static Object intercept(@Origin Method method, @AllArguments Object[] objs, @Argument(0) Object obj1, @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();

        System.out.println(objs[0].toString());

        System.out.println(obj1);

        Parameter[] parameters = method.getParameters();
        System.out.println(parameters[0].getName());

        Class<?>[] parameterTypes = method.getParameterTypes();
        System.out.println(parameterTypes[0].getName());

        System.out.println("方法名称：" + method.getName());
        int parameterCount = method.getParameterCount();
        System.out.println("入参个数：" + parameterCount);
        Class<?> returnType = method.getReturnType();
        System.out.println("出参类型：" + returnType.getName());

        try {
            return callable.call();
        } finally {
            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
    }

}
