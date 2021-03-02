package leetcode.twonumsum_001;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author longyh
 * @Description: 两数之和
 * @analysis:
 * @date 2021/2/23 15:07
 */
public class TwoNumSum {

    public static void main(String[] args) {
        int[] arr = {3,2,4};
        int target = 6;

        int[] twoIndex = official_twoSum2(arr, target);
        System.out.println(Arrays.toString(twoIndex));
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] twoIndex = new int[2];
        boolean flag = false;
        for (int i = 0; i < nums.length; i++) {
            int firstNum = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int seconds = nums[j];
                if (firstNum + seconds == target) {
                    twoIndex[0] = i;
                    twoIndex[1] = j;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        return twoIndex;
    }

    public static int[] official_twoSum(int[] nums, int target) {
        int length = nums.length;

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[2];
    }

    public static int[] official_twoSum2(int[] nums, int target) {
        int length = nums.length;
        HashMap<Integer, Integer> storeData = new HashMap<>();

        for (int i = 0; i < length; i++) {
            int num = nums[i];
            int otherNum = target - num;

            if (storeData.containsKey(otherNum)) {
                return new int[]{storeData.get(otherNum), i};
            } else {
                storeData.put(num, i);
            }
        }

        return new int[2];
    }
}
