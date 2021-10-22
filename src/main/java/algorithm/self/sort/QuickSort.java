package algorithm.self.sort;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/9 4:45 下午
 */
public class QuickSort {
    /**
     * 数组中间的数做完比较的媒介
     *
     * @param arr
     */
    public static void sort(int[] arr, int leftOffset, int rightOffset) {
        int length = arr.length;

        int leftIndex = leftOffset;
        int rightIndex = rightOffset;
        int midIndex = (leftIndex + rightIndex + 1) / 2;
        int compareValue = arr[midIndex];

        while (leftIndex <= rightIndex) {
            while (arr[leftIndex] < compareValue) {// && leftIndex < midIndex) {
                leftIndex++;
            }

            while (arr[rightIndex] >= compareValue && rightIndex > midIndex) {
                rightIndex--;
            }

            if (rightIndex > leftIndex) {
                int tmp = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = tmp;
            }

            if (leftIndex == midIndex) {
                rightIndex--;
            }

            if (rightIndex == midIndex) {
                leftIndex++;
            }

        }

        if (rightIndex > 0) {
            sort(arr, 0, leftIndex);
            sort(arr, leftIndex, rightOffset - 1);
        }
//        System.out.println("leftIndex = " + leftIndex);
//        System.out.println("rightIndex = " + rightIndex);
    }


//    public static void
}
