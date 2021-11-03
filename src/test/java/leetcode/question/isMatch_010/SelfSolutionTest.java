package leetcode.question.isMatch_010;

import junit.framework.TestCase;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/19 4:54 下午
 */
public class SelfSolutionTest extends TestCase {

    public void testIsMatch() {
        String s = "aab";
        String p = "c*a*b";
        SelfSolution selfSolution = new SelfSolution();
        boolean match = selfSolution.isMatch(s, p);

        System.out.println("match = " + match);
    }
}