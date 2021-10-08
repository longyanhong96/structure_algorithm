package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/23 4:36 下午
 */
public class Utils {

    public static void main(String[] args) throws ParseException {
        String time="20210926";
        String dateFlag = getHoliday(time);
        if ("1".equals(dateFlag) || "2".equals(dateFlag)) {
            System.err.println("今天是法定节假日");
        }else {
            System.out.println("今天是工作日");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = dateFormat.parse("1594714494");
        System.out.println("date = " + date);
    }

    public static  String  getHoliday(String  time) {
        String dc = "http://tool.bitefu.net/jiari/?d=";
        String  httpUrl=new StringBuffer().append(dc).append(time).toString();
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {

        }

        if ("1".equals(result) || "2".equals(result)) {
            return "0";
        }else {
            return "1";
        }
    }
}
