package day03.re;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class FileChannelTran {

    public void testRead() throws Exception{
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(8888));
        SocketChannel sc = channel.accept();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        sc.read(buffer);
        FileChannel fc = new FileOutputStream("1.txt").getChannel();
        fc.transferFrom(sc,0,buffer.getLong());
        fc.close();
        sc.close();
        channel.close();
    }

    public void testWrite() throws Exception{
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1",8888));
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        FileChannel fileChannel = new FileInputStream("2.txt").getChannel();
        byteBuffer.asLongBuffer().put(fileChannel.size());
        channel.write(byteBuffer);
        channel.close();
        fileChannel.close();
    }
}
