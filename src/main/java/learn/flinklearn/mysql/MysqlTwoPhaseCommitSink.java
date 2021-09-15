package learn.flinklearn.mysql;

import com.alibaba.fastjson.JSONObject;
import learn.flinklearn.mysql.utils.DBConnectUtil;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.streaming.api.functions.sink.TwoPhaseCommitSinkFunction;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/15 3:30 下午
 */
public class MysqlTwoPhaseCommitSink extends TwoPhaseCommitSinkFunction<String, Connection, Void> {

    // 传一个配置内容，需要写入的database和table
    private volatile List<String> databases;
    private volatile List<String> tables;

    public MysqlTwoPhaseCommitSink(TypeSerializer<Connection> transactionSerializer, TypeSerializer<Void> contextSerializer) {
        super(transactionSerializer, contextSerializer);
    }

    @Override
    protected void invoke(Connection connection, String s, Context context) throws Exception {

        JSONObject sourceData = JSONObject.parseObject(s);
        String database = sourceData.getString(Constants.DATABASE);
        String table = sourceData.getString(Constants.TABLE);
        String operation = sourceData.getString(Constants.OPERATION);

        if (databases.contains(database) && tables.contains(table)) {
            JSONObject afterDataJson = sourceData.getJSONObject(Constants.AFTERDATA);
            switch (operation) {
                case Constants.INSERT:
                    String sql = DBConnectUtil.parseSqlByData(afterDataJson.keySet(), database, table);
                    
                    break;
                case Constants.DELETE:
                    break;
                case Constants.UPDATE:
                    break;
                case Constants.READ:
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    protected Connection beginTransaction() throws Exception {
        return null;
    }

    @Override
    protected void preCommit(Connection connection) throws Exception {

    }

    @Override
    protected void commit(Connection connection) {

    }

    @Override
    protected void abort(Connection connection) {

    }

}
