package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/25 14:12
 */
public class PropertiesUtil {

    public static Properties getProperties(String path){
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(path);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
