package day02.nio.channel.file;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class FileChannelDemo  {

    @Test
    public void write() throws Exception{
        // 获取文件通道。基于某种流(比如文件输出流或输入流创建)
        FileChannel channel = new FileOutputStream(new File("1.txt")).getChannel();

        ByteBuffer buffer = ByteBuffer.wrap("helloworld".getBytes());
        channel.write(buffer);

        channel.close();
    }
    @Test
    public void read() throws Exception{
        FileChannel fc = new FileInputStream(new File("1.txt")).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        //-- 文件通道可以通过位置灵活的操作数据
        //-- 文件通道底层可以使用zero copy技术
        fc.position(4);
        fc.read(buffer);
        System.out.println(new String(buffer.array()));
    }

    public void testWrite() throws Exception{
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1",8888));
        FileChannel fc = new FileInputStream("1.txt").getChannel();
        //用来发送文件大小
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.asLongBuffer().put(fc.size());
        channel.write(buffer);
        //channel为指定的目标
        fc.transferTo(0,fc.size(),channel);
        fc.close();
        channel.close();
    }

    public void testRead() throws Exception{
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(8888));
        //接收socket
        SocketChannel sc = channel.accept();
        FileChannel channel1 = new FileOutputStream("2.txt").getChannel();

        ByteBuffer buf = ByteBuffer.allocate(8);
        sc.read(buf);
        buf.flip();

        long fileSize = buf.getLong();

        System.out.println("size"+fileSize);

        channel1.transferFrom(sc,0,fileSize);

        channel.close();
        channel1.close();
    }



}
