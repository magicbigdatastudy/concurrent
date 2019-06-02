package day01.semaphore;

import java.util.concurrent.Semaphore;

//饭店有五张桌子，并提供五桌人吃饭，每来一个人就会占有一张桌子
//也就相当于占用了一个信号量，吃饭以后释放桌子，相当于释放一个信号量
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for(int i =0;i<6;i++){
            new Thread(new Restaurant(semaphore)).start();
        }
    }


}
class Restaurant implements Runnable{

    private Semaphore a;

    public Restaurant(Semaphore a) {
        this.a = a;
    }

    @Override
    public void run() {
        try{
            //获取信号量，使信号量减1
            //当信号量归零后，后来线程只能阻塞
            a.acquire();
            System.out.println("一张桌子被占用");
            Thread.sleep((long)(1000*Math.random()));
            System.out.println("桌子用完");
            a.release();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
