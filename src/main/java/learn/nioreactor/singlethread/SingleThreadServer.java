package learn.nioreactor.singlethread;

import java.io.IOException;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/6 3:14 下午
 */
public class SingleThreadServer {
    public static void main(String[] args) {
        try {
            new Thread(new Reactor(2333)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
