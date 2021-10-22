package leetcode.isMatch_010;

import junit.framework.TestCase;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/19 3:28 下午
 */
public class SolutionTest extends TestCase {

    public void testIsMatch() {
        String s = "aab";
        String p = "c*a*b";

        Solution solution = new Solution();
        boolean match = solution.isMatch(s, p);
        System.out.println("match = " + match);
    }
}