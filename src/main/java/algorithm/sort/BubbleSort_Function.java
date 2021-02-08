package algorithm.sort;

import util.MyCommonUtil;

/**
 * @author longyh
 * @Description: 冒泡排序
 * @analysis:
 * @date 2021/2/8 19:24
 */
public class BubbleSort_Function {

    public static void main(String[] args) {
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 20);
        }

        MyCommonUtil.prinitArr(arr);

        bubbleSort(arr);
        System.out.println();

        MyCommonUtil.prinitArr(arr);
    }

    private static void bubbleSort(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                int sourceNum = arr[j];
                int targetNum = arr[j + 1];
                if (sourceNum > targetNum) {
                    int tmp = sourceNum;
                    arr[j] = targetNum;
                    arr[j + 1] = tmp;
                }
            }
        }
    }
}
