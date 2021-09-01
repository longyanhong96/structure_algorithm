package learn.niolearn.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/1 5:31 下午
 */
public class GroupchatClient {

    private static SocketChannel socketChannel;
    private static Selector selector;
    private static int port = 8888;

    public static void main(String[] args) {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            selector = Selector.open();

            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            socketChannel.connect(new InetSocketAddress("localhost",port));

            while (true){
                int select = selector.select();

                if (select>0){
                    Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                    while (selectionKeyIterator.hasNext()) {
                        SelectionKey selectionKey = selectionKeyIterator.next();
                        if (selectionKey.isConnectable()) {
                            System.out.println(selectionKey.interestOps() + "连接服务器");
//                            selectionKey.
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
