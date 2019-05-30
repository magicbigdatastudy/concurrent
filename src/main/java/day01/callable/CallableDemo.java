package day01.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * callable是jdk1.5之后提供的新的线程机制，比
 * Runnable的变化：
 * 1. Call方法的返回值可以自定义
 * 2. Call的返回值可以接收到
 * 3. Call可以抛异常
 * 4. Callable线程类只能通过线程池启动,不能通过new Thread().start()启动
 */
public class CallableDemo {
    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(new C1());
        String result = future.get();
        System.out.println(result);
        service.shutdown();
    }
}
class C1 implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("线程启动");
        return "success";
    }
}
