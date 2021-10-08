package structure.self.linklist.single;

import junit.framework.TestCase;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/6 5:55 下午
 */
public class DataLinkListTest extends TestCase {
    public static DataLinkList<String> linkList;
    static {
        linkList = new DataLinkList<>();
        linkList.add("a");
        linkList.add("b");
        linkList.add("c");
        linkList.add("d");
    }

    public void testAdd() {
        DataLinkList<String> linkList = new DataLinkList<>();
        linkList.add("a");
        linkList.add("b");
        linkList.add("c");
        linkList.add("d");
        linkList.listAll();

    }

    public void testListAll() {
    }

    public void testFind(){
        DataLinkList<String> linkList = new DataLinkList<>();
        linkList.add("a");
        linkList.add("b");
        linkList.add("c");
        linkList.add("d");

        System.out.println("linkList.find(\"e\") = " + linkList.find("e"));
    }

    public void testRemove(){
        linkList.remove("g");

        linkList.listAll();
    }
}