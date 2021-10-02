package learn.fastjsonlearn;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * @author longyh
 * @Description: 属性名策略说明：
 * CamelCase策略，Java对象属性：personId，序列化后属性：personId
 * PascalCase策略，Java对象属性：personId，序列化后属性：PersonId
 * SnakeCase策略，Java对象属性：personId，序列化后属性：person_id
 * KebabCase策略，Java对象属性：personId，序列化后属性：person-id
 * @analysis:
 * @date 2021/9/27 9:48 上午
 */
public class JSONStrategyDemo {

    public static void main(String[] args) {
        Person person = new Person(1, "a", "b", "c");

        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String s = JSONObject.toJSONString(person, serializeConfig);


        System.out.println("s = " + s);


        s = "{\"id\":1,\"person_email\":\"b\",\"person_phone\":\"c\"}";
        Person person2 = JSONObject.parseObject(s, Person.class);
        System.out.println("person2 = " + person2);

        String json = "{\"id\":1,\"person_email\":\"b\",\"person_name\":\"a\",\"person_phone\":\"c\"}";
        ParserConfig parserConfig = new ParserConfig();
        parserConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        Person person1 = JSONObject.parseObject(json, Person.class, parserConfig);
        System.out.println("person1 = " + person1);

    }
}
