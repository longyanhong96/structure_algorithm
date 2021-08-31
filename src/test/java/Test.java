import java.util.Arrays;

public class Test {
    public static void main(String[] args) {

        String[][] arrays = new String[5][3];

        int tmp = 0;
        for (int i = 0; i < arrays.length; i++) {
            String[] array = arrays[i];
            for (int j = 0; j < array.length; j++) {
                if (j % 2 == 0 || i % 2 == 0) {
                    arrays[i][j] = "a";
                }
            }
        }

        for (int i = 0; i < arrays.length; i++) {
            System.out.println("Arrays.toString(arrays[i]) = " + Arrays.toString(arrays[i]));
        }
    }
}
