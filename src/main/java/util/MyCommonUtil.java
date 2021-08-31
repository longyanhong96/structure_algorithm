package util;

import java.util.List;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/2/6 21:10
 */
public class MyCommonUtil {

    public static void printList(List<Integer> list) {
        list.forEach(i -> {
            System.out.print(list.get(i) + ",");
        });
    }

    public static void prinitArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
    }

    public static int[] getArr(int length) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int) (Math.random() * length);
        }
        return arr;
    }
}
