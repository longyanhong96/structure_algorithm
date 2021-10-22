package leetcode.fib_509;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/22 5:25 下午
 */
public class Solution3 {

    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        int fib = solution.fib(0);
        System.out.println("fib = " + fib);
    }

    public int fib(int n){
        int[] arr = new int[n + 1];

        for (int i = 3; i <= n; i++) {
            if (i == 0){
                arr[i] = 0;
            }else if (i == 1){
                arr[i] = 1;
            }else {
                arr[i] = arr[i - 1] + arr[i - 2];
            }
        }
        return arr[n];
    }
}
