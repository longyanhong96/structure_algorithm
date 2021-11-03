package leetcode.question.climbStairs_070;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/25 5:58 下午
 */
public class Solution1 {
    public int climbStairs(int n) {
        int res = 0, first = 1, second = 2;
        for (int i = 3; i <= n; i++) {
            res = first + second;
            first = second;
            second = res;
        }
        if (n == 1) {
            res = first;
        } else if (n == 2) {
            res = second;
        }
        return res;
    }
}
