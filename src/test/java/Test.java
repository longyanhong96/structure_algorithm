import java.util.Arrays;

public class Test {
    public static void main(String[] args) {

//        String[][] arrays = new String[5][3];
//
//        int tmp = 0;
//        for (int i = 0; i < arrays.length; i++) {
//            String[] array = arrays[i];
//            for (int j = 0; j < array.length; j++) {
//                if (j % 2 == 0 || i % 2 == 0) {
//                    arrays[i][j] = "a";
//                }
//            }
//        }
//
//        for (int i = 0; i < arrays.length; i++) {
//            System.out.println("Arrays.toString(arrays[i]) = " + Arrays.toString(arrays[i]));
//        }

        int[] num1 = {1, 2, 3, 0, 0, 0};
        int[] num2 = {2, 5, 6};

        merge(num1, 3, num2, 3);
        System.out.println("Arrays.toString(num1) = " + Arrays.toString(num1));
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int tmp;
        int length = nums1.length;
        int n1 = 0;
        int n2 = 0;

        while (n1 < m && n2 < n) {
            if (nums1[n1] > nums2[n2]) {
                tmp = nums1[n1];
                nums1[n1] = nums2[n2];
                nums2[n2] = tmp;
            }
            n1++;
        }

        while (n1 < length) {
            nums1[n1++] = nums2[n2++];
        }

    }
}
