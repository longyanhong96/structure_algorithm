package leetcode.convert_006;

/**
 * @author longyh
 * @Description: 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
 * 输入：s = "PAYPALISHIRING", numRows = 4
 * 输出："PINALSIGYAHRPI"
 * 解释：
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 * @analysis:
 * @date 2021/6/17 4:58 下午
 */
class Solution {

    public static void main(String[] args) {
        String convert = convert("PAYPALISHIRING", 5);
        System.out.println("convert = " + convert);

    }

    public static String convert(String s, int numRows) {

        String[] split = s.split("");
        int strLength = s.length();

        int oneArrayChrNum = (numRows - 1) * 2;
        int arrayNum;
        if (strLength % oneArrayChrNum == 0) {
            arrayNum = strLength / oneArrayChrNum;
        } else {
            arrayNum = strLength / oneArrayChrNum + 1;
        }

        int columnsNum = arrayNum * (numRows - 1);
        String[][] arrays = new String[numRows][columnsNum];

        // 用于判断是竖线写入还是斜线写入，true为竖线写入，false斜线写入
        boolean flag = true;
        int x = 0;
        int y = 0;
        for (int i = 0; i < strLength; i++) {
            String chr = split[i];

            if (x == numRows-1) {
                flag = false;
            } else if (x == 0) {
                flag = true;
            }
            arrays[x][y] = chr;

            if (flag) {
                x++;
            } else {
                x--;
                y++;
            }

        }

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < arrays.length; i++) {
            String[] array = arrays[i];
            for (int j = 0; j < array.length; j++) {
                if (array[j] != null){
                    buffer.append(array[j]);
                }
            }
        }

        return buffer.toString();
    }
}