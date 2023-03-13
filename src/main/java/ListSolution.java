import java.util.*;

/**
 * @Author: longyh
 * @Description:
 * @Date: Created in 13:15 2023/2/13
 */
public class ListSolution {


    // 35 error
    public static int searchInsert(int[] nums, int target) {
        int startIndex = 0;
        int endIndex = nums.length - 1;
        int mid = (startIndex + endIndex) / 2;
        if (nums[0] > target) {
            return 0;
        } else if (nums[endIndex] < target) {
            return endIndex + 1;
        }

        while (startIndex < endIndex) {

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                startIndex = mid + 1;
            } else if (nums[mid] > target) {
                endIndex = mid - 1;
            }
            mid = (startIndex + endIndex) / 2;
        }

        return mid;
    }


    // 118
    // f(x,y) = f(x-1,y-1) + f(x-1,y)
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        // x
        for (int i = 1; i <= numRows; i++) {
            if (i == 1) {
                result.add(Collections.singletonList(1));
            } else {
                // y
                List<Integer> rows = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    int leftNum = (j - 1 < 0) ? 0 : result.get(i - 2).get(j - 1);
                    int rightNum = (j + 1 == i) ? 0 : result.get(i - 2).get(j);
                    rows.add(leftNum + rightNum);
                }
                result.add(rows);
            }
        }
        return result;
    }

    // 169
    public int majorityElement(int[] nums) {
//        return majorityElementRec(nums, 0, nums.length - 1);
        return 0;
    }


    // 228
    public static List<String> summaryRanges(int[] nums) {
        if (nums.length == 0) {
            return Collections.emptyList();
        }

        if (nums.length == 1) {
            return Collections.singletonList(String.valueOf(nums[0]));
        }

        List<String> list = new ArrayList<>();
        int slow = 0;
        int fast = 1;
        for (; fast < nums.length; fast++) {
            if (nums[fast] - nums[slow] == fast - slow) {
                continue;
            }

            if (fast - slow - 1 == 0) {
                list.add(String.valueOf(nums[slow]));
            } else {
                list.add(nums[slow] + "->" + nums[fast - 1]);
            }
            slow = fast;
        }

        if (slow == nums.length - 1) {
            list.add(String.valueOf(nums[slow]));
        } else {
            list.add(nums[slow] + "->" + nums[fast - 1]);
        }

        return list;
    }

    // 485
    public static int findMaxConsecutiveOnes(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int slow = 0;
        int fast = 0;

        int length = 0;
        while (fast < nums.length) {
            while (slow < nums.length && nums[slow] == 0) {
                slow++;
            }

            fast = slow;
            while (fast < nums.length && nums[fast] == 1) {
                fast++;
            }

            length = Math.max(length, fast - slow);
            slow = fast;
        }
        return length;
    }

    // 506
    public static String[] findRelativeRanks(int[] score) {
        int length = score.length;
        String[] desc = {"Gold Medal", "Silver Medal", "Bronze Medal"};
        int[][] arr = new int[length][2];
        for (int i = 0; i < length; i++) {
            arr[i][0] = score[i];
            arr[i][1] = i;
        }
        Arrays.sort(arr, (a, b) -> b[0] - a[0]);

        String[] ans = new String[length];
        for (int i = 0; i < arr.length; i++) {
            if (i >= 3) {
                ans[arr[i][1]] = Integer.toString(i + 1);
            } else {
                ans[arr[i][1]] = desc[i];
            }
        }

        return ans;
    }

    // 561
    public static int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length; i += 2) {
            sum += nums[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        int sum = arrayPairSum(new int[]{6,2,6,5,1,2});
        System.out.println("sum = " + sum);
    }
}
