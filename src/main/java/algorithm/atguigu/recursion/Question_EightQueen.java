package algorithm.atguigu.recursion;

/**
 * @author longyh
 * @Description: 递归解决八皇后的问题:根据棋盘的规定，摆放八个皇后，互补攻击，求出所有的解法;任意两个皇后不可以处在同一列同一行同一斜线上
 * @analysis:
 * @date 2021/1/8 2:40
 */
public class Question_EightQueen {
    public static void main(String[] args) {

        // 只保存他的每一列，每一个位置代表他的每一行
        int[] arrs = new int[8];


    }

    private static void insertArr(int[] arrs, int lineNum, int columns) {
        if (lineNum < 8) {

            if (predicateSite(arrs, columns, lineNum)) {
                arrs[lineNum] = columns;
                lineNum++;
                columns = 0;
                insertArr(arrs, lineNum, columns);
            } else {
                columns++;
                insertArr(arrs, lineNum, columns);
            }
        }
    }

    private static boolean predicateSite(int[] arrs, int columns, int lineNum) {

        for (int i = 0; i < lineNum; i++) {
            // 不能是同一列
            if (arrs[i] == columns) {
                return false;
            } else {
                // 不能在同一个斜角
                int ratio = (columns - arrs[i]) / (lineNum - i);
                if (ratio == 1 || ratio == -1) {
                    return false;
                }
            }
        }

        return true;
    }


}
