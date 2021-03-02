package algorithm.sort;

import java.util.Arrays;

/**
 * @author longyh
 * @Description: 快速排序
 * @analysis:
 * @date 2021/2/10 21:44
 */
public class QuickSort_Function {

    public static void main(String[] args) {
//        int[] arr = {6, -1, 0, -2, -5, 0, -1, 2, -4, -5};
        int[] arr = {0, -1, 0, -2, 1, 0, 2, 3};

        quickSort_atguigu(arr, 0, arr.length - 1);
//        quickSort(arr, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));
    }


    // ... i ... m ...j
    public static void quickSort1(int[] arr, int leftIndex, int rightIndex) {

        int leftposition = leftIndex;
        int rightposition = rightIndex;
        int midIndex = (leftposition + rightposition) / 2;

        int midVal = arr[midIndex];

        while (leftposition < rightposition) {
            while (midVal > arr[leftposition]) {
                leftposition++;
            }

            while (midVal < arr[rightposition]) {
                rightposition--;
            }

//            if (leftposition > rightposition) {
//                break;
//            }

//            if (leftposition < rightposition) {
            int tmp = arr[leftposition];
            arr[leftposition] = arr[rightposition];
            arr[rightposition] = tmp;
//            }

            if (arr[leftposition] == midVal) {
                rightposition--;
            }

            if (arr[rightposition] == midVal) {
                leftposition++;
            }
        }

    }

    // 这个是从中间开始
    public static void quickSort_atguigu(int[] arr, int leftIndex, int rightIndex) {
        int leftposition = leftIndex;
        int rightposition = rightIndex;
        int midIndex = (leftposition + rightposition) / 2;

        int midVal = arr[midIndex];

        while (leftposition < rightposition) {
            while (midVal > arr[leftposition]) {
                leftposition++;
            }

            while (midVal < arr[rightposition]) {
                rightposition--;
            }

            if (leftposition >= rightposition) {
                break;
            }

            int tmp = arr[leftposition];
            arr[leftposition] = arr[rightposition];
            arr[rightposition] = tmp;

            if (arr[leftposition] == midVal) {
                rightposition--;
            }

            if (arr[rightposition] == midVal) {
                leftposition++;
            }
        }

//        if (leftposition == rightposition) {
//            leftposition += 1;
//            rightposition -= 1;
//        }
//
//        if (leftIndex < rightposition) {
//            quickSort_atguigu(arr, leftIndex, rightposition);
//        }
//
//        if (rightIndex > leftposition) {
//            quickSort_atguigu(arr, leftposition, rightIndex);
//        }
    }

    public static void quickSort(int[] arr, int low, int high) {
        int i, j, temp, t;
        if (low > high) {
            return;
        }
        i = low;
        j = high;
        //temp就是基准位
        temp = arr[low];

        while (i < j) {
            //先看右边，依次往左递减
            while (temp <= arr[j] && i < j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp >= arr[i] && i < j) {
                i++;
            }
            //如果满足条件则交换
            if (i < j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
//        quickSort(arr, low, j-1);
        //递归调用右半数组
//        quickSort(arr, j+1, high);
    }

}
