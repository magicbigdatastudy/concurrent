package day01.exchanger;

import java.util.concurrent.Exchanger;

/**
 * 交换机的使用场景是两个线程做数据交换的场景
 * 注意是两个线程
 */
public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> ex = new Exchanger<>();
        new Thread(new Spy1(ex)).start();
        new Thread(new Spy2(ex)).start();
    }
}
class Spy1 implements Runnable{

    Exchanger<String> exchanger;

    public Spy1(Exchanger exchanger) {
        this.exchanger = exchanger;
    }

    public void run() {
        try{
            String info = "回眸一笑";
            String spy2 = exchanger.exchange(info);
            System.out.println(spy2+"SPY1");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

class Spy2 implements Runnable{

    Exchanger<String> exchanger;

    public Spy2(Exchanger exchanger) {
        //将数据传给对方线程
        this.exchanger = exchanger;
    }

    public void run() {
        String info = "百草不生";
        try{
            String spy1 = exchanger.exchange(info);
            System.out.println(spy1+"SPY2");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
