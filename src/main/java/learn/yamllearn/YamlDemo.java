package learn.yamllearn;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import learn.flinklearn.nebula.entity.NebulaEdge;
import learn.flinklearn.nebula.entity.NebulaTag;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/27 2:14 下午
 */
public class YamlDemo {
    public static void main(String[] args) {
//        Yaml yaml = new Yaml();
//        Map<String,Object> map= (Map<String, Object>) yaml.load(ymlStr);

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/Users/mininglamp/Documents/learn/code/structure_algorithm/src/main/resources/nebulaVE.yaml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Yaml yaml = new Yaml();
        Map map = yaml.loadAs(inputStream, Map.class);
//        System.out.println("map = " + map);

        System.out.println("map = " + map);
        String nebulaTag = JSONObject.parseObject(JSONObject.toJSONString(map)).getString("nebulaTag");
        String nebulaEdge = JSONObject.parseObject(JSONObject.toJSONString(map)).getString("nebulaEdge");
        List<NebulaTag> nebulaTags = JSONObject.parseArray(nebulaTag, NebulaTag.class);
        System.out.println("nebulaTags = " + nebulaTags);
        List<NebulaEdge> nebulaEdges = JSONObject.parseArray(nebulaEdge, NebulaEdge.class);
        System.out.println("nebulaEdges = " + nebulaEdges);



    }
}
