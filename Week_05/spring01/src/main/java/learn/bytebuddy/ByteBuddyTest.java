package learn.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;

public class ByteBuddyTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(BizMethod.class)
                .method(ElementMatchers.named("queryUserInfo"))
                .intercept(MethodDelegation.to(MonitorDemo.class))
                .make();

        // 加载类
        Class<?> clazz = dynamicType.load(ByteBuddyTest.class.getClassLoader())
                .getLoaded();

        // 反射调用
        clazz.getMethod("queryUserInfo", String.class, String.class).invoke( clazz.getDeclaredConstructor().newInstance(), "10001", "Adhl9dkl");


    }
}
