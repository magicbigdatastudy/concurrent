# Concurrent

## BlockingQueue 阻塞队列
> 概述

1. 遵循队列的原则：FIFO
2. 我们需要指定元素的存储个数。界限指定之后是不可以改变的
3. BlockingQueue是阻塞式队列的顶级接口
> 实现类

1.ArrayBlockingQueue 数组队列
    - 基于数组来实现
    - 创建时要指定容量
2.LinkedBlockingQueue 链表队列
    - 可以指定容量，也可以不指定
    - 如果指定了，那么大小不可变
    - 如果没有指定，那么默认就是Integer.MAX_VALUE
3.PriorityBlockingQueue 优先级队列
    - 如果存入元素，逐个取出时会排序
    - 元素对象必须实现Comparable接口
4.SyncBlockingQueue 同步队列
    - 每次只存储一个元素
```java
public class ArrayBlockingQueueDemo{
    
    public static void main(String[] args){
      ArrayBlockingQueue<String> queue = new ArrayBlockingQueue(5);
      queue.add("a1");
      queue.add("a2");
      queue.add("a3");
      queue.add("a4");
      queue.add("a5");
      //当队列已满的情况下添加元素会报IllegalStateException
      queue.add("a6");
      //当队列已满情况下，添加元素会返回false
      boolean b = queue.offer("a6");
      //队列已满情况下，添加元素会发生阻塞
      queue.put("a6");
      //队列已满情况下，添加元素会发生阻塞一段时间，这里是1s
      queue.offer("a6",1000,TimeUnit.MILLISECONDS);
    }
    
} 

public class PriorityBlockingQueueDemo{
    
    public static void main(String[] args){
      PriorityBlockingQueue<String> queue = new PriorityBlockingQueue(5);
      queue.add("7");
      queue.add("5");
      //可以实现排序
    }
}
```
## 并发映射
- Map->HashMap->HashTable
1. ConcurrentHashMap
    - 数组+链表结构
    - 初始容量为16，负载因子是0.75 [总数(.size())/临界值(16)>0.75 扩容两倍(桶数X2)]
    - 是一个异步线程安全的map
    - jdk1.5引入了分段锁的机制，在后续的时候在分段锁基础上引入了读写锁
    - 读锁：我们允许多个线程读，但是不允许写
    - 写锁：我们允许一个线程去写，但是不允许读操作
    - jdk1.8的时候，为提高性能，引入了无锁算法
在jdk1.8时候。为了提高我们的查询效率，引入了红黑树机制，如果一个桶中元素超过8个，那么这个时候，就会扭转成一个红黑树
2. 红黑树
    - 红黑树就一棵自平衡的二分查找树
    - 红黑树的时间复杂度是O(log n)
    - 红黑树的修正：右边不平衡，左旋，左边不平衡，右旋
        1. 当前节点为红色并且父节点以及叔父节点为红色,我们要将父节点以及叔父节点涂黑。将祖父节点涂红
        2. 当前节点为红色，并且父节点也为红色，叔父节点为黑色，另外还要满足当前节点为右子叶。我们要以当前节点进行补全
        3. 当前节点为红，并且父节点为红，叔父节点为黑，且当前节点为左子叶。我们应该以父节点进行右旋
##  执行器服务
1. 线程池
    - 核心线程
    线程池中主要用于完成主要的工作人物，核心线程在使用完成后不会销毁
        a. 线程池开始的时候是没有核心线程的，每来一个请求就会创建一个核心线程
    - 工作队列
        a. 当所有线程都被占用的事情,那么后来的任务就交给我们工作队列来存储
    - 临时线程
        a. 当工作队列也存储满了，那么后来的任务就会被临时线程，来请求执行
2. Callable
    - Runnable和Callable的区别
        1. 有无返回值：Runnable在使用的时候不需要泛型，线程执行完以后也没有返回值,Callable接口
        在使用的时候需要定义泛型，线程执行完后有返回值
        2. 执行方式：Runnable线程可以通过构建Thread对象来启动，也可以通过线程池来启动，但是Callable只能通过线程池来启动
        3. 容错机制：Runnable线程不允许抛出异常，但是Callable线程可以抛出异常
```java
public class ThreadPoolDemo{
    public static void main(String[] args){
      // corePoolSize 核心线程的数量
      // maximumPoolSize 总线程数量：核心线程+工作线程
      // keepAliveTime 临时线程存活的时间
      // unit 时间单位
      // workQueue 工作队列
      // handler 拒绝执行器
      ExecutorService es = new ThreadPoolExecutor(
             10,
             20,
             5000,
             (TimeUnit).MILLISECONDS,
             new ArrayBlockingQueue<String>(),
             (r,e)->System.out.println("满了")
      );
      Future<String> string = es.sumbit(call);
      System.out.println(string.get());
     //小队列，小池子，应用场景：大量段任务场景
     ExecutorService executorService = Executors.newFixedThreadPool(5);
     //大队列，小池子，应用场景：不适合大量的段任务场景，适用于长任务
     ExecutorService executorService1 = Executors.newCachedThreadPool();
    }
}

   
class Call implements Callable<String>{
        public void call() throws Exception{
            return "SUCCESS";
        }
}

```
## 锁
1. CountDownLatch 闭锁/线程递减锁
    - await方法会在计数减为0时，放开阻塞
    - countDown来使得计数器减1
2. CyclicBarrier 栅栏
    - 当计数器为0时，放开await阻塞
    - 每await一次，计数器减1
3. Exchanger 交换机
    - 可以交换两个线程之间的数值
4. Semaphore 信号量
    - 取信号量，使信号量减1
    - 当信号量归零后，后来线程只能阻塞
5. ReentrantLock 重入锁
    - 可以有公平锁和非公平锁模式，默认非公平锁
    - 使用lock加锁，unlock解锁
## 原子性布尔
保证属性线程的安全性
1. AtomicInteger...
## 缓冲区
NIO当中，Buffer的作用就是用于数据的存储,buffer在程序的本质是一个
数组
1. ByteBuffer...
    - capacity 容量位：标记缓冲区容量，定义好就不能再改变了
    - position 操作位：标记position所能打到最大的位置
    - limit 限制位 标记position所能打到的最大位置
    - mark 标记位 用于进行标记，可以认定之前的数据没有问题，另外需要注意，在缓冲区中没有主动启动，默认值是-1

## BIO Socket
## NIO Socket
## NIO Channel
## Selector

###### MARK 目前不熟练的知识点
- 红黑树 [X]|||||||||              30%