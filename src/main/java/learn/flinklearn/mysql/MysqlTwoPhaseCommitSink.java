package learn.flinklearn.mysql;

import com.alibaba.fastjson.JSONObject;
import learn.flinklearn.mysql.utils.DBConnectUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.common.typeutils.base.VoidSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.runtime.kryo.KryoSerializer;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.sink.TwoPhaseCommitSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/15 3:30 下午
 */

public class MysqlTwoPhaseCommitSink extends TwoPhaseCommitSinkFunction<ObjectNode, Connection, Void> {

    private static final Logger logger = LoggerFactory.getLogger(MysqlTwoPhaseCommitSink.class);

    private Properties mysqlProperties;

    // 传一个配置内容，需要写入的database和table
    private volatile List<String> databases;
    private volatile List<String> tables;


    public MysqlTwoPhaseCommitSink(Properties properties) {
        super(new KryoSerializer<>(Connection.class, new ExecutionConfig()), VoidSerializer.INSTANCE);
        this.mysqlProperties = properties;
    }


    @Override
    protected void invoke(Connection connection, ObjectNode jsonNodes, Context context) throws Exception {
        String indexData = jsonNodes.get("key").toString();
        String valueData = jsonNodes.get("value").toString();

        JSONObject sourceData = JSONObject.parseObject(valueData);
        String database = sourceData.getString(Constants.DATABASE);
        String table = sourceData.getString(Constants.TABLE);
        String operation = sourceData.getString(Constants.OPERATION);

//        if (databases.contains(database) && tables.contains(table)) {
        if (StringUtils.isNotBlank(indexData)) {
            JSONObject afterDataJson = sourceData.getJSONObject(Constants.AFTERDATA);
            boolean flag = false;
            switch (operation) {
                case Constants.INSERT:
                    flag = sqlExecute(afterDataJson, database, table, connection, operation);
                    break;
                case Constants.DELETE:
                    flag = sqlExecute(JSONObject.parseObject(indexData), database, table, connection, operation);
                    break;
                case Constants.UPDATE:
                    flag = sqlExecute(afterDataJson, database, table, connection, operation);
                    break;
                case Constants.READ:
                    flag = sqlExecute(afterDataJson, database, table, connection, operation);
                    break;
                default:
                    logger.error("operation is worn;operation:{}", operation);
                    break;
            }
            logger.debug("operation:{},database:{},table:{},data:{},exec sql result:{}", operation, database, table, afterDataJson, flag);
//        }
        }
    }

    @Override
    protected Connection beginTransaction() throws Exception {
        logger.debug("=====> beginTransaction...");
        Connection connection = DruidConnectionPool.getConnection(mysqlProperties);
//        Connection connection = DBConnectUtil.getConnection("jdbc:mysql://81.71.149.77:3306/test_cdc?characterEncoding=UTF-8", "root", "123456");
        connection.setAutoCommit(false);
        return connection;
    }

    @Override
    protected void preCommit(Connection connection) throws Exception {
        logger.debug("=====> preCommit... " + connection);
    }

    @Override
    protected void commit(Connection connection) {
        logger.debug("=====> commit... " + connection);
        DBConnectUtil.commit(connection);
    }

    @Override
    protected void abort(Connection connection) {
        logger.debug("=====> rollback... " + connection);
        DBConnectUtil.rollback(connection);
    }

    private boolean sqlExecute(JSONObject data, String database, String table, Connection connection, String op) throws SQLException {
        if (op.equals(Constants.DELETE)) {
            String sql = parseDeleteSql(data.keySet(), database, table);
            PreparedStatement deleteState = connection.prepareStatement(sql);
            setValue(data, deleteState);
            return deleteState.execute();
        } else {
            String sql = parseSql(data.keySet(), database, table);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setValue(data, preparedStatement);
            boolean flag = preparedStatement.execute();
            return flag;
        }
    }

    private String parseDeleteSql(Set<String> keySet, String database, String table) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("delete from ").append(database).append(".").append(table).append(" where ");
        keySet.forEach(key -> {
            sqlBuffer.append(key).append("=? ").append("and");
        });
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        return sqlBuffer.toString();
    }

    private String parseSql(Set<String> keySet, String database, String table) {

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

    private void setValue(JSONObject data, PreparedStatement preparedStatement) throws SQLException {
        Set<String> keySet = data.keySet();
        int off = 1;
        for (String key : keySet) {
            preparedStatement.setObject(off, data.get(key));
            off++;
        }
    }

}