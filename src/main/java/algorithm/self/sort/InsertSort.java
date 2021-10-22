package algorithm.self.sort;

/**
 * @author longyh
 * @Description: 插入排序
 * @analysis:
 * @date 2021/10/9 4:12 下午
 */
public class InsertSort {


    /**
     * 就是当前的数据和当前数据的前面的所有数据比较，直到找到前面那个数据比当前的数据小位置，并把数据放在比他小的数据之后；大于当前的数据的数，往后移动
     *
     * @param arr
     */
    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int currentValue = arr[i];
            int compareIndex = i - 1;

            while (compareIndex >= 0 && arr[compareIndex] > currentValue) {
                arr[compareIndex + 1] = arr[compareIndex];
                compareIndex--;
            }
            arr[compareIndex + 1] = currentValue;
        }
    }
}
