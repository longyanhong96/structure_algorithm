package leetcode.question.rob_198;

/**
 * @author longyh
 * @Description: 输入：[2,7,9,3,1]
 * 输出：12
 * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
 * 偷窃到的最高金额 = 2 + 9 + 1 = 12 。
 * @analysis:
 * @date 2021/10/28 6:09 下午
 */
public class Solution {

    public static void main(String[] args) {
        int[] arr = {2, 7, 9, 1, 5 ,10, 1};
        Solution solution = new Solution();
        int rob = solution.rob(arr);
        System.out.println("rob = " + rob);
    }

    public int rob(int[] nums) {
        int length = nums.length;

        if (length == 1) {
            return nums[nums.length - 1];
        } else if (length == 2) {
            return Math.max(nums[length - 1], nums[length - 2]);
        } else {
            int[] resArr = new int[length + 1];
            resArr[0] = nums[0];
            resArr[1] = Math.max(nums[0], nums[1]);
            for (int i = 2; i < length; i++) {
                resArr[i] = Math.max(resArr[i - 2] + nums[i], resArr[i - 1]);
            }
            return resArr[length - 1];
        }
    }
}
