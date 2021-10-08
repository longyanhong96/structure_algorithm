package learn.flinklearn.nebula;

import org.apache.flink.connector.nebula.connection.NebulaClientOptions;
import org.apache.flink.connector.nebula.connection.NebulaGraphConnectionProvider;
import org.apache.flink.connector.nebula.connection.NebulaMetaConnectionProvider;
import org.apache.flink.connector.nebula.sink.NebulaBatchOutputFormat;
import org.apache.flink.connector.nebula.sink.NebulaSinkFunction;
import org.apache.flink.connector.nebula.statement.ExecutionOptions;
import org.apache.flink.connector.nebula.statement.VertexExecutionOptions;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.types.Row;

import java.util.Arrays;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/22 4:42 下午
 */
public class FlinkIntoNebula {

    public static void main(String[] args) {
        NebulaClientOptions nebulaClientOptions =
                new NebulaClientOptions.NebulaClientOptionsBuilder()
                        .setGraphAddress("81.71.149.77:9669")
                        .setMetaAddress("81.71.149.77:49155")
                        .build();

        NebulaGraphConnectionProvider graphConnectionProvider =
                new NebulaGraphConnectionProvider(nebulaClientOptions);

        NebulaMetaConnectionProvider metaConnectionProvider =
                new NebulaMetaConnectionProvider(nebulaClientOptions);

        ExecutionOptions executionOptions = new VertexExecutionOptions.ExecutionOptionBuilder()
                .setGraphSpace("flinkSink")
                .setTag("player")
                .setIdIndex(0)
                .setFields(Arrays.asList("name", "age"))
                .setPositions(Arrays.asList(1, 2))
                .setBatch(2)
                .builder();

        NebulaBatchOutputFormat outPutFormat =
                new NebulaBatchOutputFormat(graphConnectionProvider, metaConnectionProvider)
                        .setExecutionOptions(executionOptions);

        NebulaSinkFunction nebulaSinkFunction = new NebulaSinkFunction(outPutFormat);


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

        DataStreamSource<String> socketTextStream = env.socketTextStream("localhost", 9999);

        SingleOutputStreamOperator<Row> mapStream = socketTextStream.map(row -> {
            String[] split = row.split(",");
            Row record = new Row(split.length);
            for (int i = 0; i < split.length; i++) {
                record.setField(i, split[i]);
            }
            return record;
        });

        mapStream.addSink(nebulaSinkFunction);


        try {
            env.execute("Write Nebula");
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
