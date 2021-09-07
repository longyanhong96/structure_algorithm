package learn.nioreactor.singlethread.client;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/7 9:59 上午
 */
public class SingleThreadClient {
    public static void main(String[] args) {
        new Thread(new NIOClient("127.0.0.1", 2333)).start();
        new Thread(new NIOClient("127.0.0.1", 2333)).start();
    }
}
