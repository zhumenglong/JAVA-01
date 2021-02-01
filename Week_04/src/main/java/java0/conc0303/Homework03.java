package java0.conc0303;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework03 {

    public static void main(String[] args) {

        /**
         * 方式0: join
         */
        AtomicInteger result0 = new AtomicInteger();
        Thread thread = new Thread(() -> {
            result0.set(sum());
        });
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(result0.get());


        /**
         * 方式1: while
         */
        AtomicBoolean resultFlag = new AtomicBoolean(false);
        AtomicInteger result1 = new AtomicInteger();
        new Thread(() -> {
            result1.set(sum());
            resultFlag.set(true);
        }).start();

        while (true) {
            if (resultFlag.get()) {
                break;
            }
        }
        System.out.println(result1);


        Callable<Integer> callable = () -> sum();

        /**
         * 方式2: FutureTask
         */
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        try {
            Integer result2 = futureTask.get();
            System.out.println(result2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /**
         * 方式3: FutureTask + ExecutorService
         */
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> submit = executorService.submit(callable);
        try {
            Integer result3 = submit.get();
            System.out.println(result3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        /**
         * 方式4: CompletableFuture
         */
        Integer result4 = CompletableFuture.supplyAsync(() -> sum()).join();
        System.out.println(result4);

        /**
         * 方式5: Lock
         */
        Object lock = new Object();
        AtomicInteger result5 = new AtomicInteger();
        Thread t5 = new Thread(() -> {
            result5.set(sum());
            synchronized (lock) {
                lock.notify();//子线程唤醒
            }
        });
        t5.start();
        try {
            synchronized (lock) {
                lock.wait();//主线程等待
            }
            System.out.println(result5.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 方式6：CountDownLatch
         */
        CountDownLatch cdl = new CountDownLatch(1);
        AtomicInteger result6 = new AtomicInteger();
        new Thread(() -> {
            result6.set(sum());
            cdl.countDown();
        }).start();

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(result6.get());

        /**
         * 方式7：BlockingQueue
         */
        BlockingQueue<Integer> queue = new ArrayBlockingQueue(1);//数组型队列，长度为1
        new Thread(() -> {
            queue.add(sum());
        }).start();
        try {
            Integer take = queue.take();
            System.out.println(take);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 方式8：CyclicBarrier
         */
        CyclicBarrier cb = new CyclicBarrier(2);
        AtomicInteger result8 = new AtomicInteger();
        new Thread(() -> {
            try {
                result8.set(sum());
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(result8.get());

        /**
         * 方式9： LockSupport
         */
        AtomicInteger result9 = new AtomicInteger();
        Thread mainThread = Thread.currentThread();
        new Thread(() -> {
            result9.set(sum());
            LockSupport.unpark(mainThread);
        }).start();
        LockSupport.park();
        System.out.println(result9.get());

        /**
         * 方式10：ReentrantLock
         */
        AtomicInteger result10 = new AtomicInteger();

        ReentrantLock lock1 = new ReentrantLock();
        Condition condition = lock1.newCondition();
        new Thread(() -> { result10.set(sum());
            condition.signal();
        }).start();
        try {
            lock1.lock();
            // 放弃CPU执行权
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock1.unlock();
        }
        System.out.println(result10.get());



    }


    private static int sum() {
        return fib(3);
    }

    private static int fib(int a) {
        if (a < 2) {
            return 1;
        }
        return fib(a - 1) + fib(a - 2);
    }
}
