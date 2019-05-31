package day02.bio;

import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BIODemo {

    /**
     * accept方法会产生阻塞，直到有一个客户端接入，阻塞放开
     * read方法也会产生阻塞阻塞放开条件前提·有数据可读
     *
     * write方法也会产生阻塞，当一方写，但另一方不读，写到一定量时，就会产生阻塞
     * @throws Exception
     */
    @Test
    public void testAccept_Read() throws Exception{
        ServerSocket socket = new ServerSocket(8888);
        Socket socket1=socket.accept();
        InputStream in = socket1.getInputStream();
        //服务端和客户端连接以后，读数据，但是客户端并不发数据
        //
        in.read();

        OutputStream out = socket1.getOutputStream();
        for(int i =0;i<10000;i++){
            out.write("helloworld".getBytes());
            System.out.println(i);
        }

        System.out.println("hello");
    }

    /**
     * connect也会产生阻塞，阻塞放开的条件是真正连接或有异常
     * 抛出
     * @throws Exception
     */
    @Test
    public void testConnect() throws Exception{
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",8888));
        System.out.println("hello");
    }
}
