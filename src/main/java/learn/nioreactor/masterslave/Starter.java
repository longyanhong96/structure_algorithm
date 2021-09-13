package learn.nioreactor.masterslave;

import java.io.IOException;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/7 3:59 下午
 */
public class Starter {

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(2333)).start();
    }

}