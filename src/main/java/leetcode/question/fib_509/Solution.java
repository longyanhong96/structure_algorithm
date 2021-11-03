package leetcode.question.fib_509;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/22 12:08 下午
 */
public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int fib = solution.fib(6);
        System.out.println("fib = " + fib);
    }

    public int fib(int n) {
        if (n == 0){
            return 0;
        }else if (n == 1){
            return 1;
        }
        return fib(n-1) + fib(n-2);
    }
}
