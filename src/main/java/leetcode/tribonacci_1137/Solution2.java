package leetcode.tribonacci_1137;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/22 6:09 下午
 */
public class Solution2 {

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        int tribonacci = solution2.tribonacci(25);
        System.out.println("tribonacci = " + tribonacci);
    }

    public int tribonacci(int n) {
        int[] arr = new int[5];
        for (int i = 1; i <= n; i++) {
            if (i < 3) {
                arr[i] = 1;
            } else {
                arr[i % 5] = arr[(i - 1) % 5] + arr[(i - 2) % 5] + arr[(i - 3) % 5];
            }
        }
        return arr[n % 5];
    }
}
