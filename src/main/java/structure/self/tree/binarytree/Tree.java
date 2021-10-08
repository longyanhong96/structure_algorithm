package structure.self.tree.binarytree;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/10/8 5:33 下午
 */
public interface Tree {

    /**
     * 前序遍历
     */
    void preOrder();

    /**
     * 中序遍历
     */
    void infixOrder();

    /**
     * 后序遍历
     */
    void postOrder();

}
