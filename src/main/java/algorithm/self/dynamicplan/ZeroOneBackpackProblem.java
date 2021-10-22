package algorithm.self.dynamicplan;

/**
 * @author longyh
 * @Description: 0-1背包问题
 * @analysis: 状态转换方程： f[i,j] = Max{ f[i-1,j-Wi]+Pi( j >= Wi ), f[i-1,j] }
 * f[i,j]表示在前i件物品中选择若干件放在承重为 j 的背包中，可以取得的最大价值。
 * @date 2021/10/18 10:43 上午
 */
public class ZeroOneBackpackProblem {

    // {'a', 'b', 'c', 'd', 'e'}
    private String[] things;
    // 6,3,5,4,6
    private int[] value;
    // 2,2,6,5,4
    private int[] weight;

    private int[][] F;

    // bagSize:10
    public int backpackAnswer(int bagSize) {
        int length = things.length;

        for (int i = 0; i < bagSize; i++) {
            for (int j = 0; j < length; j++) {

            }
        }
        return 0;
    }
}
