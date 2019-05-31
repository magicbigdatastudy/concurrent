package day02.nio.channel.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerStart {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(8888));
        //获取多路复用选择器
        Selector selector = Selector.open();
        //在服务端通道上绑定监听器，并监听接入事件
        channel.register(selector, SelectionKey.OP_ACCEPT);
        while(true){
            //这个方法是一个阻塞事件，阻塞放开的条件是有事件发生
            selector.select();
            //如果代码走到这，说明阻塞放开，说明有事件发生。即需要处理事件
            Set<SelectionKey> keys = selector.selectedKeys();
            // 获取键集的迭代器
            Iterator<SelectionKey> it = keys.iterator();
            System.out.println(keys.size());
            while(it.hasNext()){
                //获取一个事件
                SelectionKey sk = it.next();
                if(sk.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) sk.channel();
                    //建立与对应客户端之间的连接
                    SocketChannel sc =server.accept();
                    //设置非阻塞模式
                    sc.configureBlocking(false);
                    //-- OP_READ 0000 0001
                    //-- OP_WRITE0000 0100
                    //--         0000 0101
                    System.out.println("接入客户端，处理线程号"+Thread.currentThread().getId());
                    sc.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                }
                //表示客户端有数据发给服务端，我们服务端要读数据
                if(sk.isReadable()){
                    SocketChannel sc = (SocketChannel)sk.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(11);
                    sc.read(buffer);
                    System.out.println("接收到客户端数据"+new String(buffer.array())+"线程号"+Thread.currentThread().getId());
                }
                // 表示客户端准备好接收数据，服务端要写数据
                if(sk.isWritable()){
                    SocketChannel sc = (SocketChannel)sk.channel();
                    ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes());
                    //因为write和read是非阻塞方法
                    //为了确保读和写的完整
                    while (buffer.hasRemaining()) {
                        sc.write(buffer);
                    }
                    System.out.println("发送数据");
                }
                //事件处理完后，移除此事件，避免重复处理
                it.remove();
            }
        }
    }
}
