package algorithm.sort;

import java.util.Arrays;

/**
 * @author longyh
 * @Description: 归并排序
 * @analysis: 这个类似于 二叉树递归深度遍历
 * @date 2021/6/11 5:49 下午
 */
public class MergeSort_Function {

    public static void main(String[] args) {
        int[] arrs = {9,8, 7, 6, 5, 4, 3, 2, 1};
        sort(arrs, 0, arrs.length - 1, new int[arrs.length]);
        System.out.println(Arrays.toString(arrs));
    }

    private static void sort(int[] arrs, int left, int right, int[] tmpArr) {
        if (left < right) {
            // 切出中间的位置
            int mid = (left + right) / 2;
            sort(arrs, left, mid, tmpArr);
            sort(arrs, mid + 1, right, tmpArr);
            merge(arrs, left, mid, right, tmpArr);
        }
    }

    private static void merge(int[] arrs, int left, int mid, int right, int[] tmpArr) {
        int l = left;
        int m = mid + 1;

        int t = 0;

        while (l <= mid && m <= right) {
            if (arrs[l] < arrs[m]) {
                tmpArr[t++] = arrs[l++];
            } else {
                tmpArr[t++] = arrs[m++];
            }
        }

        while (l <= mid) {
            tmpArr[t++] = arrs[l++];
        }

        while (m <= right) {
            tmpArr[t++] = arrs[m++];
        }

        l=left;
        t = 0;
        while (l <= right) {
            arrs[l++] = tmpArr[t++];
        }
    }
}
