package java0.conc0303;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class Homework03 {

    public static void main(String[] args) {

        /**
         * 方式1
         */
        AtomicBoolean resultFlag = new AtomicBoolean(false);
        AtomicInteger result1 = new AtomicInteger();
        new Thread(()->{
            result1.set(sum());
            resultFlag.set(true);
        }).start();

        while (true){
            if(resultFlag.get()){
                break;
            }
        }
        System.out.println(result1);


        Callable<Integer> callable = ()->sum();
        /**
         * 方式2
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
         * 方式3
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
        }

        /**
         * 方式4
         */
        Integer result4 = CompletableFuture.supplyAsync(() -> sum()).join();
        System.out.println(result4);

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
