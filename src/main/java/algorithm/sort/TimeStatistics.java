package algorithm.sort;

import util.MyCommonUtil;

/**
 * @author longyh
 * @Description: 时长的比较
 * @analysis:
 * @date 2021/2/9 2:17
 */
public class TimeStatistics {

    public static void main(String[] args) {
        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();
        SelectSort_Function.selectSort(MyCommonUtil.getArr(100000));
        endTime = System.currentTimeMillis();
        System.out.println("选择排序排序所需要时长：" + (endTime - startTime));

        startTime = System.currentTimeMillis();
        BubbleSort_Function.bubbleSort(MyCommonUtil.getArr(100000));
        endTime = System.currentTimeMillis();
        System.out.println("冒泡排序所需要时长：" + (endTime - startTime));

        startTime = System.currentTimeMillis();
        InsertSort_Function.insertSort_atguigu(MyCommonUtil.getArr(100000));
        endTime = System.currentTimeMillis();
        System.out.println("插入排序所需要时长：" + (endTime - startTime));

        startTime = System.currentTimeMillis();
        ShellSort_Function.shellSort(MyCommonUtil.getArr(100000));
        endTime = System.currentTimeMillis();
        System.out.println("希尔排序所需要时长：" + (endTime - startTime));
    }
}
