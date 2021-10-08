package learn.flinklearn.utils;


import com.twitter.chill.thrift.TBaseSerializer;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.runtime.state.memory.MemoryStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/17 9:52 上午
 */
public class FlinkUtils {

    public static StreamExecutionEnvironment env;

    private static void initEnv() {
        env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        //checkpoint设置
        //每隔10s进行启动一个检查点【设置checkpoint的周期】
        env.enableCheckpointing(10000);
        //设置模式为：exactly_one，仅一次语义
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        //确保检查点之间有1s的时间间隔【checkpoint最小间隔】
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(1000);
        //检查点必须在10s之内完成，或者被丢弃【checkpoint超时时间】
        env.getCheckpointConfig().setCheckpointTimeout(10000);
        //同一时间只允许进行一次检查点
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        //表示一旦Flink程序被cancel后，会保留checkpoint数据，以便根据实际需要恢复到指定的checkpoint
        //env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        env.setStateBackend(new FsStateBackend("file:///Users/mininglamp/Documents/learn/code/structure_algorithm/checkpoint"));
//        env.setStateBackend(new MemoryStateBackend());
    }

//    public static <T> DataStream<T> createKafkaStream()

    public static <T> DataStream<T> createKafkaStreamWithCheckpoint(ParameterTool parameters, KafkaDeserializationSchema<T> deserializer,
                                                                    Properties kafkaProperties, List<String> topics) throws Exception {
        initEnv();
        if (parameters != null) {
            env.getConfig().setGlobalJobParameters(parameters);

        }

        //KafkaSource
        FlinkKafkaConsumer<T> kafkaConsumer = new FlinkKafkaConsumer<T>(
                topics,
                deserializer,
                kafkaProperties);


        kafkaConsumer.setCommitOffsetsOnCheckpoints(true);
        return env.addSource(kafkaConsumer);
    }

    /**
     * 从kafka读取数据
     *
     * @return
     */
    public static <T> DataStream<T> createKafkaStreamWithCheckpoint(ParameterTool parameters, DeserializationSchema<T> valueDeserializer,
                                                                    Properties kafkaProperties, List<String> topics) throws Exception {
        initEnv();
        if (parameters != null) {
            env.getConfig().setGlobalJobParameters(parameters);

        }

        //KafkaSource
        FlinkKafkaConsumer<T> kafkaConsumer = new FlinkKafkaConsumer<T>(
                topics,
                valueDeserializer,
                kafkaProperties);


        kafkaConsumer.setCommitOffsetsOnCheckpoints(true);
        return env.addSource(kafkaConsumer);
    }

    public static StreamExecutionEnvironment getEnv() {
        return env;
    }

    public static void executorFlink(String jobName) throws Exception {
        env.execute(jobName);
    }
}
