package algorithm.self.sort;

import junit.framework.TestCase;
import util.MyCommonUtil;

import java.util.Arrays;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/9 6:18 下午
 */
public class QuickSortTest extends TestCase {

    public void testSort() {

//        int[] arr = {3, 3, 0, 7, 2, 9, 7, 1, 9, 3};
        int[] arr = MyCommonUtil.getArr(30);
        System.out.println(Arrays.toString(arr));
        QuickSort.sort(arr, 0, arr.length - 1);
//        QuickSort.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}