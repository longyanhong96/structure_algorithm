package algorithm.self;

import util.MyCommonUtil;

/**
 * @Description
 * @Author longyh
 * @Date 2022/8/5 9:51
 */
public class QuickSort {

    public void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int startIndex, int endIndex) {
        int leftposition = startIndex;
        int rightposition = endIndex;

        int midIndex = (leftposition + rightposition) / 2;
        int midNum = arr[midIndex];

        while (leftposition < rightposition) {
            while (arr[leftposition] <= midNum) {
                leftposition++;
            }

            while (arr[rightposition] > midNum) {
                rightposition--;
            }

            if (rightposition > leftposition) {
                int tmp = arr[leftposition];
                arr[leftposition] = arr[rightposition];
                arr[rightposition] = tmp;
            }

//            startIndex++;
//            endIndex--;
        }
        // 1 2 3 4
        if (startIndex < rightposition) {
            quickSort(arr, startIndex, rightposition);
        }

        if (endIndex > leftposition) {
            quickSort(arr, leftposition, endIndex);
        }
    }


    public void quickSortFirst(int[] arr, int startIndex, int endIndex) {
        int leftposition = startIndex;
        int rightposition = endIndex;

        int firstValue = arr[startIndex];

        while (leftposition < rightposition) {
            while (arr[leftposition] <= firstValue && leftposition < rightposition) {
                leftposition++;
            }

            while (arr[rightposition] >= firstValue && leftposition < rightposition) {
                rightposition--;
            }

            if (leftposition > rightposition){

            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {21, 78, 96, 90, 95, 45, 27, 52, 22, 26, 87, 98, 94, 17, 21, 40, 82, 81, 48, 81};

        QuickSort quickSort = new QuickSort();
//        int[] arr = MyCommonUtil.getArr(20);
        quickSort.sort(arr);
        MyCommonUtil.prinitArr(arr);
    }
}
