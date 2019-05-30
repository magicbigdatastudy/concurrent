package day01.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    static String name="li lei";

    static String gender="nan";

    public static void main(String[] args) {
        //创建重入锁
        //底层支持两种锁机制：公平锁机制和非公平锁
        /*公平锁：
        在一个线程抢到锁时，如果这个线程一直持有锁，其他新来线程要按部就班的等着，按照顺序排队。
        此时这线程执行完了，突然来了个新线程，新线程也需要排队，而
        最前面的线程接着被唤醒去执行

        1线程取得锁
        2 3 4等着
        此时1没有锁了，来了线程5
        5要继续跟在4后面,唤醒2线去执行
              劣势：吞吐量很低
              假设cpu做一次运算需要1s
              假设线程唤醒需要2s
              1. 运行一次，用时1s
               唤醒2s
              2. 运行一次，用户1s
              吞吐量： 0.5次/s (1+2+1)/2一次
          非公平锁：
          在一个线程抢到锁，如果这个线程一直持有锁，其他新来线程排队。此时线程执行完，不再持有锁
          突然新来个线程，这个线程就可以直接去取得锁去执行，这样不需要唤醒，效率更高

          1线程取得锁
          2 3 4等着
          此时1没有锁了，来了线程5
          5可以直接取得锁，而不是唤醒2线程
          1 运行1次，用时1s
          2 唤醒2s，跑2s
          3 运行一次 耗时1s
          吞吐量：1次/s （1+1+2-2）/2一次

          劣势：可能会造成某些线程一直处于饥饿状态
         *
         */
        // 参数如果为false 就是非公平锁机制，默认是非公平锁
        // 如果为true是公平锁机制
        // 注意：使用重入锁，一定要注意锁释放的问题
        // 需要在finally(lock unlock),避免死锁问题
        // 官方建议，如果使用非公平锁，建议使用sync代码块
        Lock lock = new ReentrantLock(false);
        new Thread(new WriteRunner(lock)).start();
        new Thread(new ReadRunner(lock)).start();
    }

}

class WriteRunner implements Runnable{

    private Lock lock;

    public WriteRunner(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        while(true){
            lock.lock();
            if(LockDemo.name.equals("li lei")){
                LockDemo.name = "han mei mei";
                LockDemo.gender="nv";
            }else{
                LockDemo.name="li lei";
                LockDemo.gender="nan";
            }
            lock.unlock();
        }
    }
}
class ReadRunner implements Runnable{

    private Lock lock;

    public ReadRunner(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        while(true){
            System.out.println("name is "+LockDemo.name+" gender is "+LockDemo.gender);
        }
    }
}
