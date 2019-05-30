package day02.bio.before;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientStart {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1",8888));
        socket.getOutputStream().write("hello".getBytes());
        while (true);
    }
}
