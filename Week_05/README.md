## 作业内容

> Week05 lesson7作业题目：

1、（选做）使 Java 里的动态代理，实现一个简单的 AOP。
[AopByGuava.java](/spring01/src/main/java/learn/aop/AopByGuava.java)

2、（必做）写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）,
提交到 Github。
```xml
 <bean id="foo" class="learn.biz.Foo"/>
```
注解配置：[Bar.java](/spring01/src/main/java/learn/biz/Bar.java)

3、（选做）实现一个 Spring XML 自定义配置，配置一组 Bean，例如： Student/Klass/School。
```xml
<bean id="school" class="learn.beans.School">
        <constructor-arg name="name" value="Tsinghua University"/>
        <property name="classes">
            <list>
                <bean id="class1" class="learn.beans.Klass">
                    <constructor-arg name="grade" value="1"/>
                    <property name="students">
                        <list>
                            <bean id="student1" class="learn.beans.Student">
                                <constructor-arg name="name" value="麦兜"/>
                            </bean>
                        </list>
                    </property>
                </bean>
            </list>
        </property>
    </bean>
```

4、（选做，会添加到高手附加题）

4.1 （挑战）讲网关的 frontend/backend/filter/router 线程池都改造成 Spring 配置方式；

4.2 （挑战）基于 AOP 改造 Netty 网关，filter 和 router 使用 AOP 方式实现；

4.3 （中级挑战）基于前述改造，将网关请求前后端分离，中级使用 JMS 传递消息；

4.4 （中级挑战）尝试使用 ByteBuddy 实现一个简单的基于类的 AOP；
[ByteBuddyTest.java](/spring01/src/main/java/learn/bytebuddy/ByteBuddyTest.java)

4.5 （超级挑战）尝试使用 ByteBuddy 与 Instrument 实现一个简单 JavaAgent 实现无侵入 下的 AOP。