package day01.map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
 * 学习并发Hash的创建和使用
 * HashMap
 */
public class ConcurrentMapDemo {

    /*
    *ConcurrentHashMap引入了分段锁机制，底层分了16段
    * (segment)理论上并发性能要比Hash高16倍
    * 每个Segment都可以看作是一个HashTable。即每个线程
    * 操作某个K,V时，只会锁此kv所在的Segment。而不锁其他的
    * Segment
    *
    * 此外，ConcurrentHashMap的存储方法和HashMap一致，
    * 而且负载因子的概念和HashMap一致
    *
    * 此外，底层的链表结构也和HashMap一致
    *
    *
    * 注意：以上所讲的是jdk1.8版本之前的ConcurrentHashMap
    *
    * jdk1.8之后，用到无锁算法(Compare And Swap),底层把链表换成红黑树
     */

    public static void main(String[] args) throws Exception {
        ConcurrentMap<Integer,Integer> map = new ConcurrentHashMap<>();

    }
}
