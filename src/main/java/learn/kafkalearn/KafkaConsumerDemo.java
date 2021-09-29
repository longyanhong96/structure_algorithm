package learn.kafkalearn;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import util.PropertiesUtil;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/25 15:20
 */
public class KafkaConsumerDemo {
    public static void main(String[] args) {
        Properties properties = PropertiesUtil.getProperties("E:\\workspace\\mystudy\\structure_algorithm\\src\\main\\resources\\kafkaconsumer.properties");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records)
                System.out.println("record = " + record);
        }
    }
}
