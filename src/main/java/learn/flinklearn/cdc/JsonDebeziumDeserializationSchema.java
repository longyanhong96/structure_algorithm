package learn.flinklearn.cdc;

import com.alibaba.fastjson.JSONObject;
import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import io.debezium.data.Envelope;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.List;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/14 10:08 上午
 */
public class JsonDebeziumDeserializationSchema implements DebeziumDeserializationSchema<String> {
    @Override
    public void deserialize(SourceRecord sourceRecord, Collector<String> collector) throws Exception {
        JSONObject result = new JSONObject();

        String topic = sourceRecord.topic();
        String[] split = topic.split("\\.");
        String database = split[1];
        String table = split[2];


        Struct keyStuct = (Struct) sourceRecord.key();
        if (keyStuct != null) {
            Schema schema = keyStuct.schema();
            List<Field> fields = schema.fields();
            fields.forEach(field -> {
                result.put(field.name(),keyStuct.get(field.name()));
            });
        }
        Struct struct = (Struct) sourceRecord.value();
        Struct before = struct.getStruct("before");
        JSONObject beforeJson = new JSONObject();
        if (before != null) {
            Schema schema = before.schema();
            List<Field> fieldList = schema.fields();

            for (Field field : fieldList) {
                beforeJson.put(field.name(), before.get(field.name()));
            }
        }
        result.put("before", beforeJson);

        Struct after = struct.getStruct("after");
        JSONObject afterJson = new JSONObject();
        if (after != null) {
            Schema schema = after.schema();
            List<Field> fieldList = schema.fields();
            for (Field field : fieldList) {
                afterJson.put(field.name(), after.get(field.name()));
            }
        }
        result.put("after", afterJson);

        Envelope.Operation operation = Envelope.operationFor(sourceRecord);

        result.put("type", operation);
        result.put("database", database);
        result.put("table", table);
        collector.collect(result.toJSONString());

    }

    @Override
    public TypeInformation<String> getProducedType() {
        return BasicTypeInfo.STRING_TYPE_INFO;
    }
}
