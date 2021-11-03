import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException, IOException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//        long time = 1595323577435l;
//
//        String format = dateFormat.format(new Date(time));
//        System.out.println("format = " + format);

//        BufferedReader reader = new BufferedReader(new FileReader("/Users/mininglamp/Documents/learn/code/structure_algorithm/src/main/resources/tag.csv"));
//        reader.readLine();
//        String line = null;
//        while((line=reader.readLine())!=null){
//            String item[] = line.split(",");
//            String last = item[item.length-1];
//            System.out.println(last);
//        }

        String str = "WEB,WECHAT_EMPTY_TITLE,6,,,,";
        String[] arr = str.split(",");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        String key = arr[0] + "," + arr[1] + "," + arr[3];
        String value = arr[4] + "," + arr[5] + "," + arr[6];

    }
}
