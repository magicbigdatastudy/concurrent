package day02.nio.channel.test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientStart {

    public static void main(String[] args) throws Exception{
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1",8888));

        ByteBuffer buffer = ByteBuffer.wrap("hello,world".getBytes());

        channel.write(buffer);

        while (true);
    }
}
