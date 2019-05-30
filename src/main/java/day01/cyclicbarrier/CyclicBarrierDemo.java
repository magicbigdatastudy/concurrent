package day01.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * 赛马：
 * 有两个线程
 *
 * 可以实现线程的同步协调。实现的方式就是通过await()实现
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(2);
        new Thread(new Horse1(barrier)).start();
        new Thread(new Horse2(barrier)).start();

    }

}

class Horse1 implements Runnable{

    CyclicBarrier barrier;

    Horse1(CyclicBarrier barrier){
        this.barrier = barrier;
    }


    @Override
    public void run() {
        try{
            System.out.println("赛马1来到栅栏前");
            //--该方法会产生阻塞，阻塞放开的条件时栅栏的初始计数器=0
            //--此外，此方法每调用一次，计数器-1
            barrier.await();
            System.err.println("赛马1开始跑");
        }catch (Exception e){

        }
    }
}

class Horse2 implements Runnable{

    CyclicBarrier barrier;

    Horse2(CyclicBarrier barrier){
        this.barrier = barrier;
    }


    @Override
    public void run() {
        System.out.println("正在拉肚子");
        try{
            Thread.sleep(5000);
            barrier.await();
            System.out.println("赛马2到达栅栏前");
            System.out.println("赛马2开始赛跑");
        }catch (Exception e){

        }

    }
}
