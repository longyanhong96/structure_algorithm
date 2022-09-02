package algorithm.self;

import util.MyCommonUtil;

/**
 * @Description
 * @Author longyh
 * @Date 2022/8/5 9:24
 */
public class InsertSort {

    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int lastNum = arr[i];
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > lastNum) {
                    arr[j + 1] = arr[j];
                }
            }
        }
    }

    public static void main(String[] args) {
        InsertSort insertSort = new InsertSort();

        int[] arr = MyCommonUtil.getArr(50);
        insertSort.sort(arr);

        MyCommonUtil.prinitArr(arr);
    }
}
