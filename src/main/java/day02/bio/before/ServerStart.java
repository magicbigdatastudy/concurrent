package day02.bio.before;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目前通信模型并不能应对高并发和海量请求的场景
 * 因为会创建的大量线程，最后可能会造成服务端的内存
 * 溢出
 *
 */
public class ServerStart {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);

        while(true){
            Socket socket = serverSocket.accept();
            new Thread(new ClientRunner(socket)).start();
        }
    }
}
class ClientRunner implements Runnable{

    Socket socket;

    public ClientRunner(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            InputStream in = socket.getInputStream();
            byte[] bytes=new byte[1024];
            in.read(bytes);
            System.out.println(new String(bytes));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
