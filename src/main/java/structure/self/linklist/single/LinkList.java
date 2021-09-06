package structure.self.linklist.single;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/9/6 5:06 下午
 */
interface LinkList<T> {

    public void add(T t);

    public void remove(T t);

    // 改
//    public void change(T t);

    public T find(T t);
}
