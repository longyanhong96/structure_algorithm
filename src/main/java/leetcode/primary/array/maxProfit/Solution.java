package leetcode.primary.array.maxProfit;

/**
 * @author longyh
 * @Description:
 * @analysis: 贪心算法，找出阶段的最低和阶段的最高;（但是贪心算法不会）
 * @date 2021/11/3 3:34 下午
 */
public class Solution {

    public static void main(String[] args) {
        int[] arr = {7, 1, 5, 3, 6, 4};
        Solution solution = new Solution();
        int maxProfit = solution.maxProfit(arr);
        System.out.println("maxProfit = " + maxProfit);
    }


    public int maxProfit(int[] prices) {
        int first = 0, second = 0;
        int res = 0;
        boolean flag = false;
        while (first < prices.length) {
            while (first + 1 < prices.length && prices[first] > prices[first + 1]) {
                first++;
            }

            while (second + 1 < prices.length && prices[second] < prices[second + 1]) {
                second++;
            }

            if (second <= first) {
                second++;
                continue;
            }

            if (second > first) {
                res = res + prices[second] - prices[first];
                first = second;
            }

        }
        return res;
    }
}
