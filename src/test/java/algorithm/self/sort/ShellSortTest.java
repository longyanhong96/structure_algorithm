package algorithm.self.sort;

import junit.framework.TestCase;
import util.MyCommonUtil;

import java.util.Arrays;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/15 10:31 上午
 */
public class ShellSortTest extends TestCase {

    public void testSort() {
//        int[] arr = {17, 9, 13, 1, 1, 1, 7, 3, 12, 17, 2, 9, 15, 9, 7, 4, 9, 11, 5, 10};
        int[] arr = MyCommonUtil.getArr(20);
        System.out.println(Arrays.toString(arr));
        ShellSort.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}