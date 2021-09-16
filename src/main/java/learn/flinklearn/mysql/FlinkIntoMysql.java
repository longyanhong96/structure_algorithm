package learn.flinklearn.mysql;

import com.alibaba.fastjson.JSONObject;
import learn.flinklearn.mysql.utils.YamlUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.runtime.state.memory.MemoryStateBackend;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.TwoPhaseCommitSinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;
import org.apache.flink.streaming.util.serialization.TypeInformationKeyValueSerializationSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/15 3:36 下午
 * <p>
 * JSONKeyValueDeserializationSchema
 */
public class FlinkIntoMysql {

    private static final Logger logger = LoggerFactory.getLogger(FlinkIntoMysql.class);

    private volatile static List<String> kafkaTopicList;

    private volatile static Properties mysqlProperties;

    private volatile static Properties kafkaProperties;

    public static void main(String[] args) throws Exception {

        // 解析一个配置文件，或许mysql，Kafka topic,kafka配置等内容
        initParameter(args);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        //设置并行度，为了方便测试，查看消息的顺序，这里设置为1，可以更改为多并行度
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
//        env.setStateBackend(new FsStateBackend("file:///Users/mininglamp/Documents/learn/code/structure_algorithm/checkpoint"));
        env.setStateBackend(new MemoryStateBackend());


//        FlinkKafkaConsumer<Tuple2<String, String>> kafkaConsumer = new FlinkKafkaConsumer<Tuple2<String, String>>(kafkaTopicList,
//                new TypeInformationKeyValueSerializationSchema(String.class, String.class, new ExecutionConfig()), kafkaProperties);

//        DataStreamSource<Tuple2<String, String>> kafkaSourceStream = env.addSource(kafkaConsumer);

        DataStreamSource<ObjectNode> kafkaSourceStream = env.addSource(new FlinkKafkaConsumer<>(kafkaTopicList,
                new JSONKeyValueDeserializationSchema(false), kafkaProperties));

//        kafkaSourceStream.print();

        kafkaSourceStream.addSink(new MysqlTwoPhaseCommitSink(mysqlProperties));

        env.execute();
    }


    private static void initParameter(String[] args) {
        ParameterTool params = ParameterTool.fromArgs(args);

        if (StringUtils.isBlank(params.get(Constants.CONFIGPATH_PARAM))) {
            throw new IllegalArgumentException("缺少必要参数：" + Constants.CONFIGPATH_PARAM);
        }

        String configPath = params.get(Constants.CONFIGPATH_PARAM);
        logger.debug("configPath: {}", configPath);

        parseConfig(configPath);
    }


    private static void parseConfig(String configPath) {
        YamlUtil yamlUtil = new YamlUtil(configPath);

        mysqlProperties = new Properties();
        mysqlProperties.put(Constants.MYSQL_DRIVERNAME, yamlUtil.getValueByKey(Constants.MYSQL_DRIVERNAME_KEY, ""));
        mysqlProperties.put(Constants.MYSQL_URL, yamlUtil.getValueByKey(Constants.MYSQL_URL_KEY, ""));
        mysqlProperties.put(Constants.MYSQL_USERNAME, yamlUtil.getValueByKey(Constants.MYSQL_USERNAME_KEY, ""));
        mysqlProperties.put(Constants.MYSQL_PASSWORD, yamlUtil.getValueByKey(Constants.MYSQL_PASSWORD_KEY, ""));

        kafkaProperties = new Properties();
        kafkaProperties.put(Constants.KAFKA_BOOTSTRAPSERVER, yamlUtil.getValueByKey(Constants.KAFKA_BOOTSTRAPSERVER_KEY, ""));
        kafkaProperties.put(Constants.KAFKA_GROUPID, yamlUtil.getValueByKey(Constants.KAFKA_GROUPID_KEY, ""));
        kafkaProperties.put(Constants.KAFKA_KEYDESERIALIZER, yamlUtil.getValueByKey(Constants.KAFKA_KEYDESERIALIZER_KEY, ""));
        kafkaProperties.put(Constants.KAFKA_VALUEDESERIALIZER, yamlUtil.getValueByKey(Constants.KAFKA_VALUEDESERIALIZER_KEY, ""));
        kafkaProperties.put(Constants.KAFKA_OFFSETRESET, yamlUtil.getValueByKey(Constants.KAFKA_OFFSETRESET_KEY, "earliest"));

        String kafkaTopic = yamlUtil.getValueByKey(Constants.KAFKA_TOPICS, "");
        kafkaTopicList = Arrays.asList(kafkaTopic.split(","));
    }
}
