package day02.nio.channel.selector;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ClientSelector {

    public static void main(String[] args) throws Exception{
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress("127.0.0.1",8888));
        Selector selector = Selector.open();
        sc.register(selector, SelectionKey.OP_CONNECT);

        while (true){
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();

            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()){
                SelectionKey selectionKey = it.next();

                //表示客户端成功的连接了服务端
                if(selectionKey.isConnectable()){
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    channel.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);

                }
                if(selectionKey.isReadable()){

                }
                if(selectionKey.isWritable()){

                }
            }

        }
    }
}
