package algorithm.recursion;

/**
 * @author longyh
 * @Description: 递归解决八皇后的问题:根据棋盘的规定，摆放八个皇后，互补攻击，求出所有的解法;任意两个皇后不可以处在同一列同一行同一斜线上
 * @analysis:
 * @date 2021/1/8 2:40
 */
public class Question_EightQueen {
    public static void main(String[] args) {
        int[][] map = getMap();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
        System.out.println(predicateSite(map, 3, 2));
//        System.out.println(predicateRow(map, 0, 0,8));
//        System.out.println(predicateColumn(map, 0, 0, 8));

    }

    private static int[][] getMap() {
        int[][] map = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                map[i][j] = 0;
            }
        }
        map[3][2] = 1;
        map[5][0] = 1;
        return map;
    }

    private static boolean predicateSite(int[][] map, int i, int j) {
        int lineLength = map.length;
        if (predicateRow(map, i, j, lineLength) ||
                predicateColumn(map, i, j, lineLength) ||
                predicateHypotenuse(map, i, j, lineLength)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 判断这一列中，所有的行是否存在一个皇后
     *
     * @param map        棋盘
     * @param j          该列
     * @param lineLength 棋盘长
     * @return
     */
    private static boolean predicateRow(int[][] map, int thisI, int j, int lineLength) {
        for (int i = 0; i < lineLength; i++) {
            if (thisI == i) {
                continue;
            }
            if (map[i][j] == 1) {
                return true;
            }
        }

        return false;
    }

    // 判断这一行中，所有的列是否存在一个皇后
    private static boolean predicateColumn(int[][] map, int i, int thisJ, int lineLength) {
        for (int j = 0; j < lineLength; j++) {
            if (thisJ == j) {
                continue;
            }
            if (map[i][j] == 1) {
                return true;
            }
        }

        return false;
    }

    //abs(n-i)=abs(array[n] - array[i])
    private static boolean predicateHypotenuse(int[][] map, int i, int j, int lineLength) {
        int tmpI = i + 1;
        int tmpJ = j + 1;
        while (lineLength > tmpI && lineLength > tmpJ) {
            if (map[tmpI][tmpJ] == 1) {
                return true;
            }
            tmpI++;
            tmpJ++;
        }
        tmpI = i - 1;
        tmpJ = j - 1;
        while (tmpI >= 0 && tmpJ >= 0) {
            if (map[tmpI][tmpJ] == 1) {
                return true;
            }
            tmpI--;
            tmpJ--;
        }
        tmpI = i + 1;
        tmpJ = j - 1;
        while (tmpI < lineLength && 0 <= tmpJ) {
            if (map[tmpI][tmpJ] == 1) {
                return true;
            }
            tmpI++;
            tmpJ--;
        }
        tmpI = i - 1;
        tmpJ = j + 1;
        while (0 <= tmpI && lineLength >= tmpJ) {
            if (map[tmpI][tmpJ] == 1) {
                return true;
            }
            tmpI--;
            tmpJ++;
        }
        return false;
    }
}
