package algorithm.sort;

import util.MyCommonUtil;

import java.util.List;

/**
 * @author longyh
 * @Description: 选择排序
 * @analysis:
 * @date 2021/1/20 1:38
 */
public class SelectSort_Function {

//    private static void selectSort()

    public static void main(String[] args) {
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 20);
        }

        MyCommonUtil.prinitArr(arr);

        selectSort(arr);
        System.out.println();

        MyCommonUtil.prinitArr(arr);
    }

    private static void selectSort(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            int sourceNum = arr[i];
            for (int j = i+1; j < length; j++) {
                int targetNum = arr[j];
                if (sourceNum > targetNum){
                    int tmp = targetNum;
                    arr[j] = sourceNum;
                    arr[i] = tmp;
                }
            }
        }
    }

    private static void selectSort(List<Integer> needSortList) {
        int size = needSortList.size();
        for (int i = 0; i < size; i++) {
            Integer sourceNum = needSortList.get(i);
            for (int j = i + 1; j < size; j++) {
                Integer targetNum = needSortList.get(j);
                if (sourceNum > targetNum) {
                    // 交换顺序，并赋值给sourceNum
                }
            }
        }
    }
}
