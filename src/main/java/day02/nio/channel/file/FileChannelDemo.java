package day02.nio.channel.file;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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



}
