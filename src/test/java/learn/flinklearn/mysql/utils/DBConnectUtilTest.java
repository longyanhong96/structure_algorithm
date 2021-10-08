package learn.flinklearn.mysql.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;
import learn.flinklearn.mysql.Constants;
import learn.flinklearn.utils.YamlUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/15 6:22 下午
 */
public class DBConnectUtilTest extends TestCase {

    String data = "{\"sex\":\"f\",\"name\":\"qqq\",\"id\":\"202\"}";

    public void testParseSqlByData() {
        JSONObject jsonObject = JSONObject.parseObject(data);
        String sql = DBConnectUtil.parseSql(jsonObject.keySet(), "test_cdc", "user");
        System.out.println("sql = " + sql);
    }

    public void testSqlExecute() throws Exception {
        YamlUtil yamlUtil = new YamlUtil("/Users/mininglamp/Documents/learn/code/structure_algorithm/src/main/resources/application.yml");
        Properties mysqlProperties = new Properties();
        mysqlProperties.put(Constants.MYSQL_DRIVERNAME, yamlUtil.getValueByKey(Constants.MYSQL_DRIVERNAME_KEY, ""));
        mysqlProperties.put(Constants.MYSQL_URL, yamlUtil.getValueByKey(Constants.MYSQL_URL_KEY, ""));
        mysqlProperties.put(Constants.MYSQL_USERNAME, yamlUtil.getValueByKey(Constants.MYSQL_USERNAME_KEY, ""));
        mysqlProperties.put(Constants.MYSQL_PASSWORD, yamlUtil.getValueByKey(Constants.MYSQL_PASSWORD_KEY, ""));

        DataSource dataSource = DruidDataSourceFactory.createDataSource(mysqlProperties);
        Connection connection = dataSource.getConnection();

        connection.setAutoCommit(false);

        boolean execute = DBConnectUtil.sqlExecute(JSONObject.parseObject(data), "test_cdc", "user", connection, "read");
        System.out.println("execute = " + execute);

        connection.commit();


    }

    public void testDeleted() throws Exception{
        String dt = "{\"id\":\"202\"}";
        String sql = DBConnectUtil.parseDeleteSql(JSONObject.parseObject(dt).keySet(), "test_cdc", "user");
        System.out.println("sql = " + sql);
    }
}