package leetcode.fib_509;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/22 5:03 下午
 */
public class Solution2 {

    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        int fib = solution.fib(2);
        System.out.println("fib = " + fib);
    }

    public int fib(int n) {
        int[] arr = new int[n + 1];
        return haddler(n, arr);
    }

    private int haddler(int n, int[] arr) {
        if (n == 0) arr[n] = 0;

        if (n == 1) arr[n] = 1;

        if (n != 0 && arr[n] == 0) {
            arr[n] = haddler(n - 1, arr) + haddler(n - 2, arr);
        }
        return arr[n];
    }
}
