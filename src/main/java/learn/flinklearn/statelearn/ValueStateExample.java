package learn.flinklearn.statelearn;

import learn.flinklearn.utils.FlinkUtils;
import learn.flinklearn.utils.PropertiesUtil;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;

import java.util.Arrays;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/19 4:38 下午
 */
public class ValueStateExample {
    public static void main(String[] args) throws Exception {
//        DataStream<String> kafkaStream = FlinkUtils.createKafkaStreamWithCheckpoint(null, SimpleStringSchema.class,
//                PropertiesUtil.getPropertiesByFile("/Users/mininglamp/Documents/learn/code/structure_algorithm/src/main/resources/kafkaconsumer.properties"),
//                Arrays.asList("test"));

        DataStream<ObjectNode> kafkaStream = FlinkUtils.createKafkaStreamWithCheckpoint(null, new JSONKeyValueDeserializationSchema(true),
                PropertiesUtil.getPropertiesByFile("/Users/mininglamp/Documents/learn/code/structure_algorithm/src/main/resources/kafkaconsumer.properties"),
                Arrays.asList("test"));
        kafkaStream.print();

        FlinkUtils.executorFlink("test");
    }
}
