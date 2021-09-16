package learn.flinklearn.mysql.utils;

import junit.framework.TestCase;
import learn.flinklearn.mysql.Constants;

import java.util.Arrays;
import java.util.List;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/16 11:27 上午
 */
public class YamlUtilTest extends TestCase {

    public void testGetValueByKey() {
        YamlUtil yaml = new YamlUtil("/Users/mininglamp/Documents/learn/code/structure_algorithm/src/main/resources/application.yml");
        String username = yaml.getValueByKey("mysql.username", "");
        String bootstrapServers = yaml.getValueByKey("kafka.bootstrapServers", "");
        System.out.println("bootstrapServers = " + bootstrapServers);
        System.out.println("username = " + username);

        String kafkaTopic = yaml.getValueByKey(Constants.KAFKA_TOPICS,"");
        List<String> kafkaTopicList  = Arrays.asList(kafkaTopic.split(","));

        System.out.println("kafkaTopicList = " + kafkaTopicList);
    }
}