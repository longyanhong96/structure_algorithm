package leetcode.question.climbStairs_070;

import java.util.Arrays;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/25 11:51 上午
 */
public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int i = solution.climbStairs(3);
        System.out.println("i = " + i);
    }

    public int climbStairs(int n) {
        int[] arr = new int[n + 1];
        arr[1] = 1;
        arr[2] = 2;
        for (int i = 3; i <= n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        System.out.println("arr = " + Arrays.toString(arr));
        return arr[n];
    }
}
