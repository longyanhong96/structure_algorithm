package leetcode.question.minCostClimbingStairs_746;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/28 4:58 下午
 */
public class Solution1 {

    public static void main(String[] args) {
        int[] arr = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        Solution1 solution1 = new Solution1();
        int i = solution1.minCostClimbingStairs(arr);
        System.out.println("i = " + i);
    }

    public int minCostClimbingStairs(int[] cost) {
        int[] res = new int[cost.length+1];
        for (int i = 2; i <= cost.length; i++) {
            res[i] = Math.min(res[i - 2] + cost[i - 2], res[i - 1] + cost[i - 1]);
        }
        return res[cost.length];
    }
}
