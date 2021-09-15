package learn.flinklearn.mysql;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;
import org.apache.flink.streaming.util.serialization.TypeInformationKeyValueSerializationSchema;
import scala.Tuple2;

import java.util.List;
import java.util.Properties;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/15 3:36 下午
 *
 * JSONKeyValueDeserializationSchema
 */
public class FlinkIntoMysql {

    private volatile static List<String> kafkaTopicList;

    private volatile static Properties kafkaProperties;

    public static void main(String[] args) {

        // 解析一个配置文件，或许mysql，Kafka topic,kafka配置等内容

        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        FlinkKafkaConsumer<Tuple2<String, String>> kafkaConsumer = new FlinkKafkaConsumer<Tuple2<String, String>>(kafkaTopicList,
                new TypeInformationKeyValueSerializationSchema(String.class, String.class, new ExecutionConfig()), kafkaProperties);

        DataStreamSource<Tuple2<String, String>> kafkaSourceStream = env.addSource(kafkaConsumer);

//        kafkaSourceStream.ad
    }
}
