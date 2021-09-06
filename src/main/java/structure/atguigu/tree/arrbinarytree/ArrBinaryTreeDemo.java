package structure.atguigu.tree.arrbinarytree;

/**
 * @author longyh
 * @Description:
 * @analysis:
 * @date 2021/2/18 23:41
 */
public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};

        ArrbinaryTree tree = new ArrbinaryTree(arr);
//        tree.preOrder();
//        tree.infixOrder();
        tree.postOrder();
    }
}
