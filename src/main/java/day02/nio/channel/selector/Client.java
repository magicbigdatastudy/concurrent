package day02.nio.channel.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    public static void main(String[] args) throws Exception{
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress("127.0.0.1",8888));
        //-- 这样处理是为避免非阻塞连接带来的空指针异常的问题
        while(!sc.isConnected()){
            sc.finishConnect();
            System.out.println("connecting");
        }
        ByteBuffer buffer = ByteBuffer.wrap("hello,world".getBytes());
        sc.write(buffer);
        ByteBuffer reBuffer = ByteBuffer.allocate(5);
        while (true) {
            sc.read(reBuffer);
            System.out.println("get" + new String(reBuffer.array()));
        }
    }
}
