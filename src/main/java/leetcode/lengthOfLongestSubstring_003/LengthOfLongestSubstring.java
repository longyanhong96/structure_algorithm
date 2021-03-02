package leetcode.lengthOfLongestSubstring_003;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author longyh
 * @Description: 无重复字符的最长子串 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * 输入: s = "abcabcbb"
 * 输出: 3
 * @analysis:
 * @date 2021/3/1 22:13
 */
public class LengthOfLongestSubstring {

    public static void main(String[] args) {
        String str = "pwwkew";
        int maxLength = lengthOfLongestSubstring(str);
        System.out.println("maxLength = " + maxLength);
    }

    public static int lengthOfLongestSubstring(String s) {
        Set<String> existsChar = new HashSet<>();

        int maxLength = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            String value = String.valueOf(chars[i]);
            if (existsChar.contains(value)){
                if (existsChar.size() > maxLength){
                    maxLength = existsChar.size();
                }
                existsChar.clear();
            }
            existsChar.add(value);
        }

        return maxLength;
    }
}
