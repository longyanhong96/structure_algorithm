package algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author longyh
 * @Description: 插入排序
 * @analysis:
 * @date 2021/1/18 1:53
 */
public class InsertSort_Function {

    public static void main(String[] args) {
        Random random = new Random();

        List<Integer> needsortList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            needsortList.add(random.nextInt(10));
//        }

        needsortList.add(1);
        needsortList.add(2);
        needsortList.add(3);
        needsortList.add(0);


//        int[] arr = {1, 4, 6, 8, 9, 2, 3, 4};
//        insertSort(arr);


        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 20);
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }

        System.out.println();
        insertSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
//        System.out.println("before sort needsortList = " + needsortList);
//        insertSort(needsortList);
//        System.out.println("needsortList = " + needsortList);
    }

    /**
     * i的游标是作为这个要插入的数据，也是一个有序列表和无序列表的分界线；
     * j是有序列表的游标，主要和i游标下要插入的点作比较
     * 只有i游标小于j游标才会插入；如果i游标比所有j游标下的都大，那么就不插入，因为i游标会向下走，这个自动补充在最后
     *
     * @param needSortList
     */
    private static void insertSort(List<Integer> needSortList) {
        int size = needSortList.size();
        for (int i = 0; i < size; i++) {
            Integer sourceNum = needSortList.get(i);
            for (int j = 0; j < i; j++) {
                Integer hadSortNum = needSortList.get(j);
                if (sourceNum < hadSortNum) {
                    needSortList.remove(i);
                    needSortList.add(j, sourceNum);
                    break;
                }
            }
        }
    }

    // ...j...i，我的
    public static void insertSort(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            int sourceNum = arr[i];
            for (int j = 0; j < i; j++) {
                int hadSortNum = arr[j];
                if (sourceNum < hadSortNum) {
                    int tmp = i;
                    int tmpValue;
                    while (tmp > j) {
                        tmpValue = arr[tmp];
                        arr[tmp] = arr[tmp - 1];
                        arr[tmp - 1] = tmpValue;
                        tmp--;
                    }
                    break;
                }
            }
        }
    }

    /**
     * 这一个方法，主要是比较i-1和i，如果i小于i-1，就开始一个一个比较，往前面移动，这样比我的少了很多次比较
     * ...i-1 i
     * @param arr
     */
    public static void insertSort_atguigu(int[] arr){
        int length = arr.length;
        for (int i = 1; i < length; i++) {
            int insertVal = arr[i];
            int insertIndex = i - 1;

            while (insertIndex >= 0 && insertVal < arr[insertIndex]){
                arr[insertIndex+1] = arr[insertIndex];
                insertIndex--;
            }
            arr[insertIndex+1]=insertVal;
        }
    }
}
