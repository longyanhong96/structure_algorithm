package leetcode.question.tribonacci_1137;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/22 6:29 下午
 */
public class Solution3 {
    public int tribonacci(int n) {
        int res = 0, first = 0, second = 1, third = 1;

        for (int i = 3; i <= n; i++) {
            res = first + second + third;

        }

        return 0;
    }
}
