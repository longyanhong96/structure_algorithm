package learn.flinklearn.mysql;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/16 10:07 上午
 */
public class DruidConnectionPool {

    private static DataSource dataSource = null;

    public static Connection getConnection(Properties properties) throws Exception {
        if (dataSource != null) {
            return dataSource.getConnection();
        } else {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            return dataSource.getConnection();
        }
    }

}
