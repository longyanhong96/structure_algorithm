package leetcode.question.tribonacci_1137;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/22 5:46 下午
 */
public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int res = solution.tribonacci(25);
        System.out.println("res = " + res);
    }

    public int tribonacci(int n) {
        int[] arr = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            if (i < 3) {
                arr[i] = 1;
            } else {
                arr[i] = arr[i - 1] + arr[i - 2] + arr[i -3];
            }
        }

        return arr[n];
    }
}
