package algorithm.atguigu.sort;

/**
 * @author longyh
 * @Description: 希尔排序
 * @analysis:
 * @date 2021/1/20 23:34
 */

import util.MyCommonUtil;

/**
 * 问题场景：
 * 如果一个数组{2，3，4，5，6，1}，对于1来说，位置很靠后，如果插到最前面，就需要移动很多次数
 * 结论：当需要插入的数很小的时候，后移的次数明显增多，对效率有影响
 * <p>
 * 解决场景：
 * 按下标的一定增量分组，对每组使用直接插入排序算法排序，随着增量逐渐减少，每组包好的关键词越来越多，当增量减至1时，
 * 整个文件恰被分成一组，算法终止
 * 也就是，对于上面数组1来说，{4，1}，先和4交换；然后{2，1，4}，再和2交换；整体的后移次数明显减少
 */
public class ShellSort_Function {


    public static void main(String[] args) {
//        int[] arr = {2,3,4,5,6,1};
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};

        shellSort(arr);
        MyCommonUtil.prinitArr(arr);

//        int length = arr.length;
//        int interval = length / 2;
//        for (int i = 0; i < length; i++) {
//            int insertVal = arr[i];
//            int insertIndex = i - interval;
//
//            while (insertIndex >= 0 && insertVal <= arr[insertIndex]) {
//                arr[insertIndex + interval] = arr[insertIndex];
//                insertIndex -= interval;
//            }
//            arr[insertIndex + interval] = insertVal;
//        }
//
//        System.out.println("第一次排序结果：");
//        MyCommonUtil.prinitArr(arr);
//
//
//        interval /= 2;
//        for (int i = 0; i < length; i++) {
//            int insertVal = arr[i];
//            int insertIndex = i - interval;
//
//            while (insertIndex >= 0 && insertVal <= arr[insertIndex]) {
//                arr[insertIndex + interval] = arr[insertIndex];
//                insertIndex -= interval;
//            }
//            arr[insertIndex + interval] = insertVal;
//        }
//
//        System.out.println("第二次排序结果：");
//        MyCommonUtil.prinitArr(arr);
    }

    public static void shellSort(int[] arr) {
        int length = arr.length;
        int interval = length / 2;
        int count = 1;
        while (interval > 0) {
            for (int i = 0; i < length; i++) {
                int insertVal = arr[i];
                int insertIndex = i - interval;

                while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                    arr[insertIndex + interval] = arr[insertIndex];
                    insertIndex -= interval;
                }
                arr[insertIndex + interval] = insertVal;
            }
            System.out.print("第" + count + "次排序结果：");
            MyCommonUtil.prinitArr(arr);
            System.out.println();
            count++;
            interval /= 2;
        }
    }
}
