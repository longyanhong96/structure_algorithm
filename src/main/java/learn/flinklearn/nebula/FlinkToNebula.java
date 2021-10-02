package learn.flinklearn.nebula;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.nebula.connection.NebulaClientOptions;
import org.apache.flink.connector.nebula.connection.NebulaGraphConnectionProvider;
import org.apache.flink.connector.nebula.connection.NebulaMetaConnectionProvider;
import org.apache.flink.connector.nebula.statement.EdgeExecutionOptions;
import org.apache.flink.connector.nebula.statement.ExecutionOptions;
import org.apache.flink.connector.nebula.statement.VertexExecutionOptions;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.types.Row;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/26 4:09 下午
 */
public class FlinkToNebula {
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

        ExecutionOptions playerExecutionOptions = new VertexExecutionOptions.ExecutionOptionBuilder()
                .setGraphSpace("test")
                .setTag("player")
                .setIdIndex(0)
                .setFields(Arrays.asList("name", "age"))
                .setPositions(Arrays.asList(1, 2))
                .setBatch(2)
                .builder();

        ExecutionOptions teamExecutionOptions = new VertexExecutionOptions.ExecutionOptionBuilder()
                .setGraphSpace("test")
                .setTag("team")
                .setIdIndex(0)
                .setFields(Arrays.asList("name"))
                .setPositions(Arrays.asList(1))
                .setBatch(2)
                .builder();

        ExecutionOptions followExecutionOptions = new EdgeExecutionOptions.ExecutionOptionBuilder()
                .setGraphSpace("test")
                .setEdge("follow")
                .setSrcIndex(0)
                .setDstIndex(1)
                .setRankIndex(2)
                .setFields(Arrays.asList("degree"))
                .setPositions(Arrays.asList(3))
                .setBatch(2)
                .builder();

        ExecutionOptions friendExecutionOptions = new EdgeExecutionOptions.ExecutionOptionBuilder()
                .setGraphSpace("test")
                .setEdge("friend")
                .setSrcIndex(0)
                .setDstIndex(1)
                .setRankIndex(2)
                .setFields(Arrays.asList("src", "dst", "degree", "start"))
                .setPositions(Arrays.asList(0, 1, 3, 4))
                .setBatch(2)
                .builder();

        List<ExecutionOptions> executionOptionsList = new ArrayList<>();
        executionOptionsList.add(playerExecutionOptions);
        executionOptionsList.add(teamExecutionOptions);
        executionOptionsList.add(followExecutionOptions);
        executionOptionsList.add(friendExecutionOptions);

        NebulaVertexEdgeBatchOutputFormat<Tuple2<String, Row>> nebulaVertexEdgeBatchOutputFormat =
                new NebulaVertexEdgeBatchOutputFormat<Tuple2<String, Row>>(graphConnectionProvider, metaConnectionProvider)
                        .setExecutionOptions(executionOptionsList);

        NebulaVertexEdgeSinkFunction<Tuple2<String, Row>> nebulaVertexEdgeSinkFunction = new NebulaVertexEdgeSinkFunction<>(nebulaVertexEdgeBatchOutputFormat);


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

        DataStreamSource<String> socketTextStream = env.socketTextStream("localhost", 9999);

        SingleOutputStreamOperator<Tuple2<String, Row>> mapStream = socketTextStream.map(new MapFunction<String, Tuple2<String, Row>>() {
            @Override
            public Tuple2<String, Row> map(String row) throws Exception {
                String[] split = row.split(",");
                Row record = new Row(split.length - 1);
                for (int i = 0; i < split.length - 1; i++) {
                    record.setField(i, split[i]);
                }
                Tuple2<String, Row> tuple2 = new Tuple2<>();
                tuple2.setFields(split[split.length - 1], record);
                return tuple2;
            }
        });
//                row -> {
//            String[] split = row.split(",");
//            Row record = new Row(split.length - 1);
//            for (int i = 0; i < split.length; i++) {
//                record.setField(i, split[i]);
//            }
//            Tuple2<String, Row> tuple2 = new Tuple2<>();
//            tuple2.setFields(split[split.length - 1]);
//            return Tuple2.of(split[split.length - 1], record);
//        });

        mapStream.addSink(nebulaVertexEdgeSinkFunction);

        try {
            env.execute("Write Nebula");
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
