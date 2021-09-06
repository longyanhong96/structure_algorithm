package structure.atguigu.tree.arrbinarytree;

/**
 * @author longyh
 * @Description: 顺序存储二叉树
 * @analysis:
 * @date 2021/2/18 18:31
 */
public class ArrbinaryTree {

    private int[] arr;

    public ArrbinaryTree(int[] arr) {
        this.arr = arr;
    }

    public void preOrder() {
        preOrder(0);
    }

    public void preOrder(int index) {
        if (arr.length > index) {
            System.out.println(arr[index]);
        }

        if (arr.length > (2 * index + 1)) {
//            System.out.println(arr[2*index+1]);
            preOrder(2 * index + 1);
        }

        if (arr.length > (2 * index + 2)) {
            preOrder(2 * index + 2);
        }
    }

    public void infixOrder(){
        infixOrder(0);
    }

    public void infixOrder(int index){
        if (arr.length > (2 * index + 1)) {
//            System.out.println(arr[2*index+1]);
            infixOrder(2 * index + 1);
        }

        if (arr.length > index) {
            System.out.println(arr[index]);
        }

        if (arr.length > (2 * index + 2)) {
            infixOrder(2 * index + 2);
        }
    }

    public void postOrder(){
        postOrder(0);
    }

    public void postOrder(int index){
        if (arr.length > (2 * index + 1)) {
//            System.out.println(arr[2*index+1]);
            postOrder(2 * index + 1);
        }

        if (arr.length > (2 * index + 2)) {
            postOrder(2 * index + 2);
        }

        if (arr.length > index) {
            System.out.println(arr[index]);
        }
    }
}
