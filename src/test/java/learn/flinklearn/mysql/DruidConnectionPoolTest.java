package learn.flinklearn.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import junit.framework.TestCase;
//import learn.flinklearn.utils.YamlUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/16 3:32 下午
 */
public class DruidConnectionPoolTest extends TestCase {

    public void testGetConnection() throws Exception {

//        YamlUtil yamlUtil = new YamlUtil("/Users/mininglamp/Documents/learn/code/structure_algorithm/src/main/resources/application.yml");
        Properties mysqlProperties = new Properties();
//        mysqlProperties.put(Constants.MYSQL_DRIVERNAME, yamlUtil.getValueByKey(Constants.MYSQL_DRIVERNAME_KEY, ""));
//        mysqlProperties.put(Constants.MYSQL_URL, yamlUtil.getValueByKey(Constants.MYSQL_URL_KEY, ""));
//        mysqlProperties.put(Constants.MYSQL_USERNAME, yamlUtil.getValueByKey(Constants.MYSQL_USERNAME_KEY, ""));
//        mysqlProperties.put(Constants.MYSQL_PASSWORD, yamlUtil.getValueByKey(Constants.MYSQL_PASSWORD_KEY, ""));


        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://81.71.149.77:3306/test_cdc?characterEncoding=UTF-8");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); //这个可以缺省的，会根据url自动识别
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        DataSource ds = DruidDataSourceFactory.createDataSource(mysqlProperties);
        Connection connection = dataSource.getConnection();
        System.out.println("connection = " + connection);
    }
}