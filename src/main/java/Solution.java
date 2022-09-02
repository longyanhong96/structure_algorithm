import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author longyh
 * @Date 2022/8/31 10:02
 */
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        boolean happy = solution.isSubsequence("axc", "ahbgdc");
//        System.out.println("happy = " + happy);

//        char[] chars = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
//        int compress = solution.compress(chars);
//        System.out.println("compress = " + compress);
//        System.out.println(Arrays.toString(chars));


//        int[] nums1 = {0};
//        solution.merge(nums1, 0, new int[]{1}, 1);
//        System.out.println("Arrays.toString(nums1) = " + Arrays.toString(nums1));

//        boolean words = solution.validPalindrome("abcdca");
//        System.out.println("words = " + words);

        boolean compare = solution.backspaceCompare("ab##", "c#d#");
        System.out.println("compare = " + compare);
    }

    public int[] twoSum(int[] nums, int target) {
        int i = 0, targetNum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (; i < nums.length; i++) {
            int num = nums[i];
            targetNum = target - num;

            if (map.containsKey(targetNum)) {
                break;
            } else {
                map.put(num, i);
            }
        }
        return new int[]{i, map.get(targetNum)};
    }


    public boolean isHappy(int n) {
        int sum = getSum(n);
        while (sum != 1) {
            sum = getSum(sum);
            if (sum < 10 && sum != 1) {
                return false;
            }
        }
        return true;
    }

    private int getSum(int n) {
        int sum = 0;
        while (n > 0) {
            int tail = n % 10;
            sum += tail * tail;
            n /= 10;
        }
        return sum;
    }


    public boolean isSubsequence(String s, String t) {
        int slow = 0, fast = 0;
        for (; fast < t.length(); fast++) {
            char targetChar = t.charAt(fast);
            if (s.charAt(slow) == targetChar) {
                slow++;

                if (slow == s.length()) {
                    return true;
                }
            }
        }
        return false;
    }

    public int compress(char[] chars) {
        int slow = 0, fast = 0;
        for (; fast < chars.length; fast++) {
            char fastChar = chars[fast];
            char slowChar = chars[slow];
            if (fastChar != slowChar) {
                if (fast - slow != 1) {
                    int num = fast - slow;
                    chars[++slow] = (char) num;
                    chars[++slow] = fastChar;
                } else {
                    slow++;
                }
            }
        }
        return slow + 1;
    }


    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] tmp = new int[m + n];

        int t1 = 0, t2 = 0, tmpIndex = 0;
        while (t1 < m && t2 < n) {
            if (nums1[t1] >= nums2[t2]) {
                tmp[tmpIndex++] = nums2[t2++];
            } else {
                tmp[tmpIndex++] = nums1[t1++];
            }
        }

        while (t1 < m) {
            tmp[tmpIndex++] = nums1[t1++];
        }

        while (t2 < n) {
            tmp[tmpIndex++] = nums2[t2++];
        }

        for (int i = 0; i < tmpIndex; i++) {
            nums1[i] = tmp[i];
        }
    }

    /**
     * 557
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        int fast = 0, slow = 0;
        StringBuffer buffer = new StringBuffer();
        for (; fast < s.length(); fast++) {
            char c = s.charAt(fast);
            if (c == ' ') {
                buffer.append(reverse(s, fast - 1, slow) + " ");
                fast++;
                slow = fast;
            }
        }

        if (fast != slow) {
            buffer.append(reverse(s, fast - 1, slow));
        }
        return buffer.toString();
    }

    private String reverse(String source, int fast, int slow) {
        StringBuffer buffer = new StringBuffer();
        for (; fast >= slow; fast--) {
            buffer.append(source.charAt(fast));
        }
        return buffer.toString();
    }

    /**
     * 680
     *
     * @param s
     * @return
     */
    public boolean validPalindrome(String s) {
        int left = 0, right = s.length() - 1, remove = 1;
        while (left <= right) {
            char leftChar = s.charAt(left);
            char rightChar = s.charAt(right);
            if (leftChar != rightChar) {
                if (remove != 1) {
                    return false;
                }
                if (s.charAt(left + 1) == rightChar || leftChar == s.charAt(right - 1)) {
                    return predicate(s, left + 1, right) || predicate(s, left, right - 1);
                }

                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private boolean predicate(String source, int left, int right) {
        while (left <= right) {
            if (source.charAt(left++) != source.charAt(right--)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 844
     *
     * @param s
     * @param t
     * @return
     */
    public boolean backspaceCompare(String s, String t) {
        int index1 = s.length() - 1, index2 = t.length() - 1, remove = 0;
        while (index1 >= 0 || index2 >= 0) {
            while (s.charAt(index1) == '#') {
                remove++;
                index1--;
            }
            index1 -= remove;
            remove = 0;

            while (t.charAt(index2) == '#') {
                remove++;
                index2--;
            }
            index2 -= remove;
            remove = 0;

            if (index1 > 0 && index2 > 0 && s.charAt(index1--) != s.charAt(index2--)) {
                return false;
            }
        }

        if (index1 != index2) {
            return false;
        }
        return true;
    }
}
