package leetcode.primary.array.removeDuplicates;

import java.util.Arrays;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/11/3 12:04 下午
 */
public class Solution {

    public static void main(String[] args) {
        int[] arr = {};
        Solution solution = new Solution();
        int removeDuplicates = solution.removeDuplicates(arr);
        System.out.println(Arrays.toString(Arrays.copyOf(arr, removeDuplicates)));
        System.out.println("removeDuplicates = " + removeDuplicates);
    }

    public int removeDuplicates(int[] nums) {
        int length = nums.length;
        if (length > 0) {
            int size = 1;
            int dupNum = nums[0];

            for (int i = 1; i < length; i++) {
                int num = nums[i];
                if (dupNum != num) {
                    nums[size] = num;
                    size += 1;
                    dupNum = num;
                }
            }
            return size;
        }
        return 0;
    }
}
