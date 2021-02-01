## 作业内容

> Week04 作业题目（周三）：

1、（可选）跑一跑课上的各个例子，加深对多线程的理解
2、（可选）完善网关的例子，试着调整其中的线程池参数

> Week04 作业题目（周日）：

1、（选做）把示例代码，运行一遍，思考课上相关的问题。也可以做一些比较。
2、（必做）思考有多少种方式，在main函数启动一个新线程，运行一个方法，拿到这
个方法的返回值后，退出主线程？
[Homework03.java](src/main/java/java0/conc0303/Homework03.java)

1.join

 - Main Thread 调用 子线程join(),Main Thread 阻塞。

2.while

 - Main Thread 循环查询结果标志位。子线程设置结果标志位。

3.FutureTask

 - FutureTask#get() 阻塞 Main Thread。

4.CompletableFuture
 
 - CompletableFuture.supplyAsync().join(); 阻塞 Main Thread。

5. Object Lock

 - 配合synchronized关键字，lock.await()使 Main Thread阻塞，子线程lock.notify()唤醒 Main Thread。

6. CountDownLatch

 - CountDownLatch初始化为1，使用CountDownLatch#await()阻塞主线程，子线程CountDownLatch#countDown()唤醒。

7. CyclicBarrier

 - CyclicBarrier初始化为2，主线程和子线程均调用CyclicBarrier#await()后, 主线程被唤醒。

8. BlockingQueue
 
 - 使用BlockingQueue#take()阻塞 Main Thread。

9. LockSupport
 - Main Thread 调用LockSupport#park()阻塞自身，子线程调用LockSupport.unPark()将其唤醒。

10. ReentrantLock
 - Main Thread 调用ReentrantLock#lock()获取锁，并Condition#await()，等待子线程调用Condition#signal();