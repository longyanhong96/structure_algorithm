package learn.flinklearn.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/16 11:18 上午
 */
public class YamlUtil {

    private static Map<String, Object> properties;

    public YamlUtil(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Yaml yaml = new Yaml();
        properties = yaml.loadAs(inputStream, Map.class);
    }

    // 从String 中获取配置的方法
    public void initWithString(String content) {
        Yaml yaml = new Yaml();
        properties = yaml.loadAs(content, Map.class);
    }

    public String getValueByKey(String key){
       return getValueByKey(key,"");
    }

    public <T> T getValueByKey(String key, T defaultValue) {
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator)) {
            // 取下面配置项的情况, user.path.keys 这种
            separatorKeys = key.split("\\.");
        } else {
            // 直接取一个配置项的情况, user
            Object res = properties.get(key);
            return res == null ? defaultValue : (T) res;
        }
        // 下面肯定是取多个的情况
        String finalValue = null;
        Object tempObject = properties;
        for (int i = 0; i < separatorKeys.length; i++) {
            //如果是user[0].path这种情况,则按list处理
            String innerKey = separatorKeys[i];
            Integer index = null;
            if (innerKey.contains("[")) {
                // 如果是user[0]的形式,则index = 0 , innerKey=user
//                index = Integer.valueOf(StringTools.getSubstringBetweenFF(innerKey, "[", "]")[0]);
                index = Integer.valueOf(innerKey.split("\\[")[0].split("\\]")[0]);
                innerKey = innerKey.substring(0, innerKey.indexOf("["));
            }
            Map<String, Object> mapTempObj = (Map) tempObject;
            Object object = mapTempObj.get(innerKey);
            // 如果没有对应的配置项,则返回设置的默认值
            if (object == null) {
                return defaultValue;
            }
            Object targetObj = object;
            if (index != null) {
                // 如果是取的数组中的值,在这里取值
                targetObj = ((ArrayList) object).get(index);
            }
            // 一次获取结束,继续获取后面的
            tempObject = targetObj;
            if (i == separatorKeys.length - 1) {
                //循环结束
                return (T) targetObj;
            }

        }
        return null;
    }
}
