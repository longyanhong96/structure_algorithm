package leetcode.question.minCostClimbingStairs_746;

/**
 * @author longyh
 * @Description:
 * @analysis:
 *
 * if x>arr.length 0
 * else
 * f(x) = min{f(x+1) + v[x+1] ,f(x+2) + v[x+2]}
 *
 * @date 2021/10/25 6:02 下午
 */
public class Solution {

    public static void main(String[] args) {
        int[] arr = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        Solution solution = new Solution();
        int i = solution.minCostClimbingStairs(arr);
        System.out.println("i = " + i);
    }

    public int minCostClimbingStairs(int[] cost) {
        int res = 0;
        int index = -1;
        res = handler(cost, res, index);
        return res;
    }

    private int handler(int[] cost, int res, int index) {
        if (index > cost.length - 1) {
            return 0;
        } else {
            if (index < 0) {
                res += Math.min(handler(cost, res, index + 1), handler(cost, res, index + 2));
            } else {
                res += Math.min(handler(cost, res, index + 1) + cost[index], handler(cost, res, index + 2) + cost[index]);
            }
            return res;
        }

    }

}
