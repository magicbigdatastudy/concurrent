package day03.re;


import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    public static void main(String[] args) throws Exception{
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(8888));
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keys_I = keys.iterator();
            while(keys_I.hasNext()){
                SelectionKey key = keys_I.next();
                if(key.isAcceptable()){

                }
                if(key.isReadable()){

                }
                if(key.isWritable()){

                }
            }
        }
    }

}
