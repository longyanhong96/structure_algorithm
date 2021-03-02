package leetcode;

import javax.swing.text.TabableView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author longyh
 * @Description: 三数之和
 * @analysis:
 * @date 2021/2/27 22:40
 */
public class ThreeNumSum {

    public static void main(String[] args) {
        int[] arr = {-1,0,1,2,-1,-4};

        System.out.println(threeSum(arr));

    }

    public static List<List<Integer>> threeSum(int[] nums) {
        return threeSum(nums, 0);
    }

    public static List<List<Integer>> threeSum(int[] nums, int target) {
        int length = nums.length;
        HashMap<Integer, Integer> storeData = new HashMap<>();
        List<List<Integer>> list = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            int firstNum = nums[i];
            for (int j = i + 1; j < length; j++) {
                int secondNum = nums[j];
                int targetNum = target - firstNum - secondNum;
                if (storeData.containsKey(targetNum)) {
                    Integer targetIndex = storeData.get(targetNum);
//                    if ()
                    List<Integer> result = Arrays.asList(i, j, targetIndex);
                    list.add(result);
                } else {
                    storeData.put(firstNum, i);
                }
            }
        }

        return list;
    }
}
