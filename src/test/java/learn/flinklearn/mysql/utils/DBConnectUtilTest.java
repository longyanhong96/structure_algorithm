package learn.flinklearn.mysql.utils;

import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/15 6:22 下午
 */
public class DBConnectUtilTest extends TestCase {

    String data = "";

    public void testParseSqlByData() {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String sql = DBConnectUtil.parseSqlByData(jsonObject.keySet(), "a", "q");
        System.out.println("sql = " + sql);
    }
}