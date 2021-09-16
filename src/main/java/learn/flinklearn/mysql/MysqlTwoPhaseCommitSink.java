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
import java.util.List;
import java.util.Properties;

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
                    flag = DBConnectUtil.sqlExecute(afterDataJson, database, table, connection, operation);
                    break;
                case Constants.DELETE:
                    flag = DBConnectUtil.sqlExecute(JSONObject.parseObject(indexData), database, table, connection, operation);
                    break;
                case Constants.UPDATE:
                    flag = DBConnectUtil.sqlExecute(afterDataJson, database, table, connection, operation);
                    break;
                case Constants.READ:
                    flag = DBConnectUtil.sqlExecute(afterDataJson, database, table, connection, operation);
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

}