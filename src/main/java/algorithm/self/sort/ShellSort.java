package algorithm.self.sort;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/15 9:46 上午
 */
public class ShellSort {
    public static void sort(int[] arr) {

        int length = arr.length;
        int stepLength = length / 2;

        while (stepLength > 0) {
            for (int i = stepLength; i < length; i ++) {
                int currentValue = arr[i];
                int beforeIndex = i - stepLength;

                while (beforeIndex >= 0 && arr[beforeIndex] > currentValue ) {
                    arr[beforeIndex + stepLength] = arr[beforeIndex];
                    beforeIndex -= stepLength;
                }
                arr[beforeIndex + stepLength] = currentValue;
            }

            stepLength /= 2;
        }

    }
}
