package leetcode.fib_509;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/22 6:20 下午
 */
public class Solution4 {
    public int fib(int n) {
        int res = 1, first = 1, second = 0;
        for (int i = 2; i <= n; i++) {
            res = first + second;
            first = second;
            second = res;

        }
        return res;
    }
}
