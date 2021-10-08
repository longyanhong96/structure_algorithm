package learn.flinklearn.mysql.utils;

import com.alibaba.fastjson.JSONObject;
import learn.flinklearn.mysql.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/15 5:17 下午
 */
public class DBConnectUtil {

    /**
     * 获取连接
     *
     * @param url
     * @param user
     * @param password
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(String url, String user, String password) throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(url, user, password);
        //设置手动提交
        conn.setAutoCommit(false);
        return conn;
    }

    /**
     * 提交事务
     *
     * @param conn
     */
    public static void commit(Connection conn) {
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close(conn);
            }
        }
    }

    /**
     * 事物回滚
     *
     * @param conn
     */
    public static void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close(conn);
            }
        }
    }

    /**
     * 关闭连接
     *
     * @param conn
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean sqlExecute(JSONObject data, String database, String table, Connection connection, String op) throws SQLException {
        if (op.equals(Constants.DELETE)) {
            String sql = parseDeleteSql(data.keySet(), database, table);
            PreparedStatement deleteState = connection.prepareStatement(sql);
            setValue(data, deleteState);
            return deleteState.execute();
        } else {
            String sql = DBConnectUtil.parseSql(data.keySet(), database, table);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setValue(data, preparedStatement);
            boolean flag = preparedStatement.execute();
            return flag;
        }
    }

    public static String parseDeleteSql(Set<String> keySet, String database, String table) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("delete from ").append(database).append(".").append(table).append(" where ");
        keySet.forEach(key -> {
            sqlBuffer.append(key).append("=? ").append("and");
        });
        sqlBuffer.delete(sqlBuffer.length() - 3,sqlBuffer.length());
//        sqlBuffer.deleteCharAt(sqlBuffer.length() - 3);
        return sqlBuffer.toString();
    }

    public static String parseSql(Set<String> keySet, String database, String table) {

        StringBuffer sqlBuffer = new StringBuffer();

        StringBuffer buffer1 = new StringBuffer();
        buffer1.append(" VALUES (");

        StringBuffer buffer2 = new StringBuffer();
        buffer2.append(" ON DUPLICATE KEY UPDATE ");

        sqlBuffer.append("INSERT INTO ").append(database).append(".").append(table).append(" (");
        keySet.forEach(key -> {
            sqlBuffer.append("`").append(key).append("`").append(",");
            buffer1.append("?").append(",");
            buffer2.append("`").append(key).append("` = VALUES(`").append(key).append("`)").append(",");
        });

        buffer1.deleteCharAt(buffer1.length() - 1).append(")");
        buffer2.deleteCharAt(buffer2.length() - 1);
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1).append(")").append(buffer1).append(buffer2);
        return sqlBuffer.toString();
    }

    public static void setValue(JSONObject data, PreparedStatement preparedStatement) throws SQLException {
        Set<String> keySet = data.keySet();
        int off = 1;
        for (String key : keySet) {
            preparedStatement.setObject(off, data.get(key));
            off++;
        }
    }
}
