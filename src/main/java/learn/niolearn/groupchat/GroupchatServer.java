package learn.niolearn.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/1 10:19 上午
 */
public class GroupchatServer {

    private static ServerSocketChannel serverSocketChannel;
    private static Selector selector;
    private static int port = 8888;

    public static void main(String[] args) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket = serverSocketChannel.socket();

            selector = Selector.open();

            serverSocket.bind(new InetSocketAddress(port));

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

//            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT,new ByteBuffer[1024]);

            while(true){
                int select = selector.select();
                if (select > 0){
                    Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                    while (selectionKeyIterator.hasNext()) {
                        SelectionKey selectionKey = selectionKeyIterator.next();

                        if (selectionKey.isAcceptable()){
                            ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                            SocketChannel socketChannel = channel.accept();
                            socketChannel.configureBlocking(false);

                            System.out.println("用户" + selectionKey.interestOps() + "上线了");
                            socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                        }else if (selectionKey.isReadable()){
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();

                            int read = socketChannel.read(byteBuffer);
                            String receiveText = null;
                            if (read>0){
                                receiveText = new String(byteBuffer.array(), 0, read);
                                System.out.println("服务器端接受客户端数据--:"+receiveText);
                            }

                            if (receiveText!=null){
                                Iterator<SelectionKey> keyIterator = selector.keys().iterator();
                                while (keyIterator.hasNext()){
                                    SelectionKey key = keyIterator.next();
                                    if(key.channel() instanceof SocketChannel && key.interestOps()!=selectionKey.interestOps()){
                                        SocketChannel channel = (SocketChannel) key.channel();
                                        channel.write(ByteBuffer.wrap(receiveText.getBytes()));
                                    }
                                }
                            }

                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
