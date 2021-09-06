package structure.self.linklist.single;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/6 5:08 下午
 */
public class DataLinkList<T> implements LinkList<T> {

    private DataNode<T> first;
    private DataNode<T> lastest;
//    private int size;

    @Override
    public void add(T t) {
        if (first == null) {
            first = new DataNode<>(t, null, null);
            lastest = first;
        } else {
            //
            DataNode<T> preDataNode = lastest;
            DataNode<T> newDataNode = new DataNode<>(t, null, preDataNode);
            preDataNode.add(newDataNode);
            lastest = newDataNode;
        }
    }

    @Override
    public void remove(T t) {

    }

    @Override
    public T find(T t) {
        return first.find(t);
    }

    public void listAll() {
        if (first == null) {
            throw new NullPointerException("first dataNode is null");
        }
        first.listALl();
    }

    public class DataNode<T> {
        private T self;
        private DataNode<T> nextNode;
        private DataNode<T> preNode;

        public DataNode(T self, DataNode<T> nextNode, DataNode<T> preNode) {
            this.self = self;
            this.nextNode = nextNode;
            this.preNode = preNode;
        }

        public void add(DataNode<T> dataNode) {
            this.nextNode = dataNode;
        }

        public void remove(DataNode<T> dataNode) {

        }

        public T find(T t) {
            if (t.equals(self)) {
                return t;
            } else {
                if (nextNode != null) {
                    return nextNode.find(t);
                }else{
                    return null;
                }
            }
        }

        public void listALl() {
            System.out.println(self.toString());
            if (nextNode != null) {
                nextNode.listALl();
            }
        }

        // 改
    }
}
