package learn.flinklearn.window;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.aggregation.AggregationFunction;
import org.apache.flink.streaming.api.functions.aggregation.SumAggregator;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author longyh
 * @Description: flink 窗口类型，滑动窗口demo
 * @analysis:
 * @date 2021/9/13 10:05 上午
 */
public class SlidingWindowDemo {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

        env.setParallelism(1);

        DataStreamSource<String> dataStreamSource = env.socketTextStream("localhost", 9999);

        SingleOutputStreamOperator<Tuple2<String, Integer>> mapDataStream = dataStreamSource.map(new MapFunction<String, Tuple2<String, Integer>>() {

            private volatile SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            private volatile Random random = new Random();

            @Override
            public Tuple2<String, Integer> map(String s) throws Exception {
                long currentTimeMillis = System.currentTimeMillis();
                int randomInt = random.nextInt(10);


                System.err.println("value : " + s + " random : " + randomInt + " timestamp : " + currentTimeMillis + "|" + simpleDateFormat.format(currentTimeMillis));

                Tuple2<String, Integer> tuple2 = Tuple2.of(s, randomInt);

                return tuple2;
            }
        });

        KeyedStream<Tuple2<String, Integer>, Tuple> keyedStream = mapDataStream.keyBy(0);

        // 滑动窗口，窗口长为1分钟，滑动步长为30s；也就是 30s计算一次窗口的长度
        WindowedStream<Tuple2<String, Integer>, Tuple, TimeWindow> timeWindow = keyedStream.timeWindow(Time.minutes(1), Time.seconds(30));

        timeWindow.sum(1).print();

//        timeWindow.aggregate();

        // 这个每个key进入一次
        timeWindow.apply(new WindowFunction<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple, TimeWindow>() {

            private volatile SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

//            private volatile Map<String, Integer> countMap = new HashMap<>();

            @Override
            public void apply(Tuple tuple, TimeWindow timeWindow, Iterable<Tuple2<String, Integer>> iterable, Collector<Tuple2<String, Integer>> collector) throws Exception {
//                countMap.clear();

                long windowStart = timeWindow.getStart();
                long windowEnd = timeWindow.getEnd();

                System.err.println("windowStart:" + simpleDateFormat.format(windowStart) + ",windowEnd:" + simpleDateFormat.format(windowEnd));

                Iterator<Tuple2<String, Integer>> iterator = iterable.iterator();
//                while (iterator.hasNext()) {
//                    Tuple2<String, Integer> next = iterator.next();
//                    System.out.printf("key:%s,value:%s", next.f0, next.f1);
//                    System.out.println();
//                    if (countMap.containsKey(next.f0)) {
//                        countMap.put(next.f0, countMap.get(next.f0) + next.f1);
//                    }else {
//                        countMap.put(next.f0,next.f1);
//                    }
//                }
//
//                countMap.forEach((k,v)->{
//                    Tuple2<String, Integer> tuple2 = Tuple2.of(k, v);
//                    collector.collect(tuple2);
//                });
                String key = null;
                int value = 0;
                while (iterator.hasNext()) {
                    Tuple2<String, Integer> next = iterator.next();
                    System.out.printf("key:%s,value:%s", next.f0, next.f1);
                    System.out.println();
                    key = next.f0;
                    value += next.f1;
                }
                if (StringUtils.isNoneBlank(key)) {
                    collector.collect(Tuple2.of(key, value));
                }
            }
        }).print();


        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
