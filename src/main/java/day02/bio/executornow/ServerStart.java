package day02.bio.executornow;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 目前通信模型并不能应对高并发和海量请求的场景
 * 因为会创建的大量线程，最后可能会造成服务端的内存
 * 溢出
 * 所以人们才发明了线程池
 *
 *
 *
 */
public class ServerStart {

    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newFixedThreadPool(0x10);
        ServerSocket serverSocket = new ServerSocket(8888);

        while(true){
            Socket socket = serverSocket.accept();
            service.execute(new ClientRunner(socket));
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
            System.out.println("当前线程池的线程编号"+Thread.currentThread().getId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
