package day03.re;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Client {

    public static void main(String[] args) throws Exception{
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1",8888));
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_CONNECT);
        while (true){
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keys_I = keys.iterator();
            while (keys_I.hasNext()){
                SelectionKey key = keys_I.next();
                if(key.isConnectable()){

                }
                if(key.isWritable()){

                }
                if(key.isReadable()){

                }
            }
            keys_I.remove();
        }
    }
}
