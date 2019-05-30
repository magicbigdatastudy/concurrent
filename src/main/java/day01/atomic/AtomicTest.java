package day01.atomic;

import java.util.concurrent.CountDownLatch;

public class AtomicTest {

    public static int i = 0;

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
            AtomicTest.i++;
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
            AtomicTest.i++;
        }
        latch.countDown();
    }
}
