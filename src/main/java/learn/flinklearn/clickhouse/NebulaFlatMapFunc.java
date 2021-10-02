package learn.flinklearn.clickhouse;

import learn.flinklearn.clickhouse.entity.ESEntity;
import learn.flinklearn.clickhouse.entity.ESRelation;
import learn.flinklearn.clickhouse.entity.ESSession;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/27 10:31 上午
 */
public class NebulaFlatMapFunc implements FlatMapFunction<ESSession, Tuple2<String, Row>> {

//    private List<Tuple2<String, Row>> vertexEdgeList;

    // key: label value: 需要属性名
    private Map<String, List<String>> vertexEdgeFieldNameMap;

    public NebulaFlatMapFunc(Map<String, List<String>> vertexEdgeFieldNameMap) {
        this.vertexEdgeFieldNameMap = vertexEdgeFieldNameMap;
    }

    @Override
    public void flatMap(ESSession esSession, Collector<Tuple2<String, Row>> collector) throws Exception {
        List<ESEntity> nestedEntityList = esSession.getNestedEntity();
        List<ESRelation> nestedRelationList = esSession.getNestedRelation();

        for (ESEntity esEntity : nestedEntityList) {
            Tuple2<String, Row> entityRowTuple = parseEntityToRow(esEntity);
            if (entityRowTuple != null) {
                collector.collect(entityRowTuple);
            }
        }

        for (ESRelation esRelation : nestedRelationList) {
            Tuple2<String, Row> relationRowTuple = parseEntityToRow(esRelation);
            if (relationRowTuple != null) {
                collector.collect(relationRowTuple);
            }
        }
    }

    private Tuple2<String, Row> parseEntityToRow(Object nestObject) throws NoSuchFieldException, IllegalAccessException {
        if (nestObject instanceof ESEntity) {
            ESEntity esEntity = (ESEntity) nestObject;
            String type = esEntity.getType();
            List<String> fieldNameList = vertexEdgeFieldNameMap.get(type);

            Row row = new Row(fieldNameList.size());
            for (int i = 0; i < fieldNameList.size(); i++) {
                String fieldName = fieldNameList.get(i);
                Field field = esEntity.getClass().getField(fieldName);
                row.setField(i, field.get(field));
            }
            return Tuple2.of(type, row);
        } else if (nestObject instanceof ESRelation) {
            ESRelation esRelation = (ESRelation) nestObject;
            String type = esRelation.getType();
            List<String> fieldNameList = vertexEdgeFieldNameMap.get(type);

            Row row = new Row(fieldNameList.size());
            for (int i = 0; i < fieldNameList.size(); i++) {
                String fieldName = fieldNameList.get(i);
                Field field = esRelation.getClass().getField(fieldName);
                row.setField(i, field.get(field));
            }
            return Tuple2.of(type, row);

        }
        return null;
    }
}
