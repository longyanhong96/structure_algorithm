package algorithm.recursion;

import java.util.*;

/**
 * @author longyh
 * @Description: 用递归的方式解决迷宫的算法：就是给定一个迷宫（二维数组），给定一个出口，随机一个起始点，走到那个出口的位置
 * @analysis: tips：递归要有一个终止的地方
 * 1、1代表墙，2代表走的路线，3代表走过但是不通过
 * 2、随机一个上下左右的位置
 * @date 2021/1/7 0:47
 */
// TODO 最短路径
public class Question_Labyrinth {

    private static Random random = new Random();
    private static Map<Integer, String> randomDrection;

    private static List<String> directorList = Arrays.asList("up", "down", "left", "right");

    public static void main(String[] args) {
        int[][] labyrinthArray = getLabyrinthArray();

        setWays(labyrinthArray, 1, 1);
        int length = labyrinthArray.length;
        for (int i = 0; i < length; i++) {
            int[] ints = labyrinthArray[i];
            int length1 = ints.length;
            for (int j = 0; j < length1; j++) {
                System.out.print(ints[j]);
            }
            System.out.println();
        }
    }

    /**
     * 获取一个二维数组的迷宫
     *
     * @return 迷宫map
     */
    // TODO 后期改成给一个行和列，随机输出一个迷宫
    private static int[][] getLabyrinthArray() {
        int[][] map = new int[8][7];
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        map[3][1] = 1;
        map[3][2] = 1;
//        map[4][3] = 1;
        map[6][4] = 1;
        return map;
    }

    private static void setRandomDrection() {
        randomDrection = new HashMap<>();
        randomDrection.put(0, "up");
        randomDrection.put(1, "down");
        randomDrection.put(2, "left");
        randomDrection.put(3, "right");
    }

    /**
     * @param map 迷宫
     * @param i   起始点的行
     * @param j   起始点的列
     */
    /*
    analysis：向下一步走的时候需要是知道下一个点的位置是否可以走；如果左右上下都不通的话，就要回溯（递归）；走过的方向不走
     */
    private static boolean setWays(int[][] map, int i, int j) {
        if (map[6][5] == 2) {
            return true;
        } else {
            List<String> tmpDirectionList = new ArrayList<>();
            tmpDirectionList.addAll(directorList);
            int unmatchDirection = 0;
            boolean flag = false;
            while (!tmpDirectionList.isEmpty()) {
                // 随机获取一个方向，并获取下一步方向的值，判断是否可以走;等于0就是可以走,递归，并对现在该点赋值为2；如果所有方向都是不等于0，就是走不了，回溯，对现在的点赋值为3
                // 有点瑕疵，应该给该位置赋值，后判断是不是出口，在判断走下一步
                String direction = tmpDirectionList.get(random.nextInt(tmpDirectionList.size()));
                int nextStepNum = getNextStepNum(map, direction, i, j);
                if (nextStepNum == 0) {
                    nextStep(map, direction, i, j, 2);
                    switch (direction) {
                        case "up":
                            flag = setWays(map, i - 1, j);
                            break;
                        case "down":
                            flag = setWays(map, i + 1, j);
                            break;
                        case "left":
                            flag = setWays(map, i, j - 1);
                            break;
                        case "right":
                            flag = setWays(map, i, j + 1);
                            break;
                        default:
                            break;
                    }
                } else {
                    unmatchDirection++;
                    tmpDirectionList.remove(direction);
                }

                if (flag) {
                    break;
                }
            }
            if (unmatchDirection == 4) {
                map[i][j] = 3;
                return false;
            }
            return flag;
        }
    }

    /**
     * 获取下一步的值
     *
     * @param map       地图
     * @param direction 方向
     * @param i         现在的位置
     * @param j         现在的位置
     * @return 该方向下一步的值
     */
    private static int getNextStepNum(int[][] map, String direction, int i, int j) {
        switch (direction) {
            case "up":
                return map[i - 1][j];
            case "down":
                return map[i + 1][j];
            case "left":
                return map[i][j - 1];
            case "right":
                return map[i][j + 1];
            default:
                System.out.println("this direction is wrong");
                return -1;
        }
    }

    /**
     * 对走的一步赋值
     *
     * @param map       地图
     * @param direction 行走的方向
     * @param i         现在的位置
     * @param j         现在的位置
     * @param num       所赋的值
     */
    private static void nextStep(int[][] map, String direction, int i, int j, int num) {
        switch (direction) {
            case "up":
                map[i - 1][j] = num;
                break;
            case "down":
                map[i + 1][j] = num;
                break;
            case "left":
                map[i][j - 1] = num;
                break;
            case "right":
                map[i][j + 1] = num;
                break;
            default:
                System.out.println("this direction is wrong");
                break;
        }
    }
}
