package day02.nio.channel;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;



public class ChannelDemo {

    @Test
    public void server_create() throws Exception{
        //--获取服务器通道，最主要的作用是绑定服务ip和服务端口
        ServerSocketChannel serverSocketChannel
                = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8888));
        //变成非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //--获取和对应客户端通信的通道
        SocketChannel sc = serverSocketChannel.accept();
        System.out.println("hello,world");
    }
    @Test
    public void client_create_connect() throws Exception{
        //获取客户端通道
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress("127.0.0.1",8888));
        System.out.println("hello,world");
    }


}
