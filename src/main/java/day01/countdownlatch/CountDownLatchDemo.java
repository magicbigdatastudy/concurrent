package day01.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) throws Exception{
        CountDownLatch latch = new CountDownLatch(2);
        new Thread(new BuyGuo(latch)).start();
        new Thread(new BuyCai(latch)).start();
        latch.await();
        System.out.println("开始炒菜");
    }

}

class BuyGuo implements Runnable{

    private CountDownLatch cdl;

    public BuyGuo(CountDownLatch cdl){
        this.cdl =cdl;
    }

    @Override
    public void run() {
        System.out.println("锅买回来了");
        //--此方法每调用一次就减1
        cdl.countDown();
    }
}

class BuyCai implements Runnable{

    private CountDownLatch latch;

    public BuyCai(CountDownLatch latch){
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("菜买回来了");
        latch.countDown();
    }
}
