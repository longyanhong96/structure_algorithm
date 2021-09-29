package learn.kafkalearn;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import util.PropertiesUtil;

import java.util.Properties;
import java.util.function.Consumer;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/25 14:12
 */
public class KafkaProducerDemo {
    public static void main(String[] args) {
        Properties properties = PropertiesUtil.getProperties("E:\\workspace\\mystudy\\structure_algorithm\\src\\main\\resources\\kafkaproducer.properties");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        ProducerRecord<String , String> record = null;
        for (int i = 0; i < 100; i++) {
            record = new ProducerRecord<String, String>("test", "value"+i);
            //发送消息
            producer.send(record);
        }
    }
}
