package leetcode.question.isMatch_010;

/**
 * @author longyh
 * @Description: 如果 p.charAt(j) == s.charAt(i) : dp[i][j] = dp[i-1][j-1]；
 * 如果 p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1]；
 * 如果 p.charAt(j) == '*'：
 * 如果 p.charAt(j-1) != s.charAt(i) : dp[i][j] = dp[i][j-2] //in this case, a* only counts as empty
 * 如果 p.charAt(i-1) == s.charAt(i) or p.charAt(i-1) == '.'：
 * dp[i][j] = dp[i-1][j] //in this case, a* counts as multiple a
 * or dp[i][j] = dp[i][j-1] // in this case, a* counts as single a
 * or dp[i][j] = dp[i][j-2] // in this case, a* counts as empty
 * @analysis:
 * @date 2021/10/19 3:48 下午
 */
public class SelfSolution {
    public boolean isMatch(String s, String p) {
        int sLength = s.length();
        int pLength = p.length();

        boolean[][] f = new boolean[sLength + 1][pLength + 1];
        f[0][0] = true;
        for (int i = 1; i <= sLength; i++) {
            for (int j = 1; j <= pLength; j++) {
                if (p.charAt(j - 1) == s.charAt(i - 1) || p.charAt(j - 1) == '.') {
                    f[i][j] = f[i - 1][j - 1];
                } else if (p.charAt(j - 1) == '*') {
                    if (p.charAt(j - 2) != s.charAt(i - 1)) {
                        f[i][j] = f[i][j - 2];
                    } else if (p.charAt(j - 2) == s.charAt(i - 1) || p.charAt(j - 2) == '.') {
                        f[i][j] = f[i - 1][j] || f[i][j - 1] || f[i][j - 2];
                    }
                }
            }
        }
        return f[sLength][pLength];
    }
}
