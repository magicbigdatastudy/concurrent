package day01.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;


// 为何直接加数值会小于200000，由于++操作是拷贝一份加完了再赋值回去
// 而加完还没赋回去前，被另一个线程读取到了原来值，然后也进行操作，最后后操作
// 的把之前操作的就会覆盖
// int i = 0    [第三行的步骤发生在这之间]
// thread 1 -》 0+1 -》1->i->i= 1
// thread 2 -》0+1

/**
 * 原子性类型底层用到CAS算法(无锁算法)，即可以保证线程并发安全问题，并且
 * 性能很高
 */
public class AtomicTest {

    public static AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) throws Exception{
        CountDownLatch latch = new CountDownLatch(2);
        new Thread(new AddRunner1(latch)).start();
        new Thread(new AddRunner2(latch)).start();
        latch.await();
        System.out.println(i);
    }

}

class AddRunner1 implements Runnable{

    CountDownLatch latch;

    AddRunner1(CountDownLatch latch){
        this.latch = latch;
    }

    @Override
    public void run() {
        for(int j =0;j<100000;j++){
            AtomicTest.i.incrementAndGet();
        }
        latch.countDown();
    }
}
class AddRunner2 implements Runnable{


    CountDownLatch latch;

    AddRunner2(CountDownLatch latch){
        this.latch = latch;
    }

    @Override
    public void run() {
        for(int j =0;j<100000;j++){
            AtomicTest.i.incrementAndGet();
        }
        latch.countDown();
    }
}
