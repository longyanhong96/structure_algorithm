package learn.flinklearn.cdc;

import com.alibaba.fastjson.JSONObject;
import com.ververica.cdc.connectors.mysql.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.DebeziumSourceFunction;
import com.ververica.cdc.debezium.StringDebeziumDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/14 9:34 上午
 *
 * TypeInformationKeyValueSerializationSchema
 */
public class FlinkCDCDemo {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        final OutputTag<String> outputTag = new OutputTag<String>("side-output"){};

        DebeziumSourceFunction<String> mysqlSource = MySqlSource.<String>builder()
                .hostname("81.71.149.77")
                .databaseList("test_cdc")
                .username("root")
                .password("123456")
                .deserializer(new JsonDebeziumDeserializationSchema())
                .startupOptions(StartupOptions.initial())
                .build();


        DataStreamSource<String> dataStreamSource = env.addSource(mysqlSource);

        SingleOutputStreamOperator<String> processStream = dataStreamSource.process(new ProcessFunction<String, String>() {

            final OutputTag<String> outputTag = new OutputTag<String>("side-output") {
            };

            @Override
            public void processElement(String s, Context context, Collector<String> collector) throws Exception {

                JSONObject sourceJson = JSONObject.parseObject(s);
                if (sourceJson.getString("table").equals("user")) {
                    collector.collect(s);
                } else {
                    context.output(outputTag, String.valueOf(s));
                }
            }
        });


//        processStream.addSink(new FlinkKafkaProducer<String>())

//        new FlinkKafkaProducer<>()

        processStream.print("main");
        processStream.getSideOutput(outputTag).print("sideOut");

//        dataStreamSource.print();

        try {
            env.execute("Flink-CDC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
