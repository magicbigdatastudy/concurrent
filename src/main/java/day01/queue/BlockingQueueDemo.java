package day01.queue;

import day01.Student;

import java.util.concurrent.*;

/**
 * 学习阻塞队列的使用。阻塞队列在高并发以及很多大数据框架底层有
 * 应用。队列的使用场景：将数据存入队列，可以启动缓冲作用，消峰限流
 * 此外，队列另外的作用就是实现生产者和消费者之间的解耦
 */

public class BlockingQueueDemo {
    /*
     * add方法当队列已满，会抛异常。通过使用这种方法来插入队列，可以捕捉特定异常
     * offer方法会返回特定值，如果已满再次插入，返回false
     * put方法当队列已满。会产生阻塞。当队列有剩余容量，则阻塞放开。此方法常用
     * offer超时方法当队列已满，会产生阻塞。
     * offer(对象,阻塞时间,单位)
     * 阻塞放开有两个：
     * 1.队列有剩余容量
     * 2.过了指定的超时时间
     */
    private void TestPut() throws Exception{
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        for(int i = 0;i<10;i++){
            queue.put(i);
        }
        queue.offer(11,5, TimeUnit.SECONDS);
    }

    /*
     * remove方法 当队列为空时，会抛异常NoSuchElement,如果不为空，
     * 则按照FIFO的原则，取出相应的元素
     * poll方法当队列为空时，会抛null值.一般会根据poll返回值是否为null
     * 来判断队列是否为空
     * take方法当队列为空时，会发生阻塞
     * poll超时会产生阻塞，阻塞放开条件：
     * 1.队列有数据可取
     * 2.超时时间到达
     */
    private void TestGet() throws Exception {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        queue.poll(3, TimeUnit.SECONDS);
    }

    /*
     * ArrayBlockingQueue:数组阻塞队列的特点
     * 1.有界队列，容量在创建时指定
     * 2.底层是通过数组的数据结构实现的，所以查询快，增删慢
     * LinkedBlockingQueue:链阻塞队列的特点
     * 1.是无界队列，默认大小是Integer.MaxValue
     * 2.底层是链数据结构，增删快，因为队列的使用的一般是增或者删，所以比array常用
     *
     * 阻塞队列之所以能够保证并发安全，底层是通过锁机制实现的(重入锁)
     */
    private void createQueue(){
        BlockingQueue<Integer> q1 = new ArrayBlockingQueue<>(10);
        BlockingQueue<Integer> q2 = new LinkedBlockingQueue<>();
    }

    /*
     * 优先级队列，要求插入队里的元素必须实现Comparable接口。
     * 队列会按照CompareTo中的比较规则，实现元素的排序
     * 然后取出元素时，会以元素的顺序取出
     */
    private void createCompareQueue(){
        BlockingQueue<Student> students = new PriorityBlockingQueue<>();
        Student s1 = new Student("1",93);
        Student s2 = new Student("2",41);
        Student s3 = new Student("3",100);

        students.add(s1);
        students.add(s2);
        students.add(s3);

        for(Student s:students){
            System.out.println(s);
        }
    }
    public static void main(String[] args) throws Exception{
        new BlockingQueueDemo().createCompareQueue();
    }
}
