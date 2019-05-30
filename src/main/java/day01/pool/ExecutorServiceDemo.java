package day01.pool;

import java.util.concurrent.*;

public class ExecutorServiceDemo {


    /**
     * newCachedThreadPool:
     * 1 没有核心线程
     * 2 都是临时线程
     * 3 队列时同步队列
     * 4 最大线程数 Integer.MAX_VALUE
     * 总结：大池子小队列的好处
     * 可以很好的响应（及时）客户端请求.因为不需要等待排队。
     * 但是注意：这种线程适应于短请求。如果都是长请求，可能会导致线程一直
     * 创建而不销毁，最后内存溢出
     */
    public void create_2() {
        ExecutorService service = Executors.newCachedThreadPool();
    }

    /**
     * newFixedThreadPool
     * 1 都是核心线程，没有临时线程
     * 2 队列是无界队列LinkedBlockQueue
     * 总结：池子大，大队列
     * 这种线程池作用：消峰限流
     */
    public void create_3(){
        ExecutorService service = Executors.newFixedThreadPool(10);
    }

    public void create_1() {
        // corePoolSize 核心线程数
        // 当线程池初次创建时，是没有任何线程的，当有请求发起时，线程会
        // 创建核心线程，在请求过程中无论核心线程是否闲置，线程池都会创建核心
        // 线程，直到满足数量位置。之后新请求来的时候，存在阻塞队列，直到阻塞队列
        // 满了，再次新来的请求会创建临时线程，最终到最大线程数，之后的线程会被拒绝
        // 服务器助手处理，即拒绝线程
        // maximumPoolSize 最大线程数。核心线程数+临时线程数
        // keepAliveTime 临时线程存活时间
        // unit 时间单位，一般用ms单位
        // workQueue 等待队列，用于存放未处理请求
        ExecutorService service = new ThreadPoolExecutor(5, 10, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("拒绝请求");
            }
        });

        for(int i = 0;i<21;i++)
         service.execute(new ClientRunner());

        /**
         * 关闭线程池，此方法调用时，线程池不会接收外部的请求
         * 但内部的线程并不会马上销毁，而是等待线程工作完后销毁
         */
        service.shutdown();



    }
}
class ClientRunner implements Runnable{
    @Override
    public void run() {
        System.out.println(11111);
    }
}
