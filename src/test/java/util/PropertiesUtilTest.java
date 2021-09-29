package util;

import junit.framework.TestCase;

import java.util.Properties;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/25 14:22
 */
public class PropertiesUtilTest extends TestCase {

    public void testGetProperties() {
        Properties properties = PropertiesUtil.getProperties("E:\\workspace\\mystudy\\structure_algorithm\\src\\main\\resources\\kafkaproducer.properties");
        System.out.println("properties = " + properties);
    }
}