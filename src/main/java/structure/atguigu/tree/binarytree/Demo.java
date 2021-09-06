package structure.atguigu.tree.binarytree;

/**
 * @author longyh
 * @Description: 测试类,测试二叉树遍历，总方法
 * @analysis:
 * @date 2021/2/14 16:21
 */
public class Demo {

    public static void main(String[] args) {
        HeroTree heroTree = new HeroTree();

        HeroNode root = new HeroNode(1, "a");
        HeroNode node1 = new HeroNode(2, "b");
        HeroNode node2 = new HeroNode(3, "c");
        HeroNode node3 = new HeroNode(4, "d");
        HeroNode node4 = new HeroNode(5, "e");

        root.setLeft(node1);
        root.setRight(node2);
        node2.setLeft(node3);
        node2.setRight(node4);
        heroTree.setRootNode(root);

//        System.out.println("前序遍历");
//        heroTree.preOrder();  // a,b,c,d,e
//
//        System.out.println("中序遍历");
//        heroTree.infixOrder(); // b,a,d,c,e
//
//        System.out.println("后序遍历");
//        heroTree.postOrder(); // b,d,e,c,a

//        HeroNode node5 = new HeroNode(6, "f");
//        HeroNode node6 = new HeroNode(7, "g");
//        node1.setLeft(node5);
//        node1.setRight(node6);
//
//        HeroNode node7 = new HeroNode(8, "h");
//        node5.setLeft(node7);

//        System.out.println("前序遍历");
//        heroTree.preOrder();  // a,b,f,h,g,c,d,e
//
//        System.out.println("中序遍历");
//        heroTree.infixOrder(); // h,f,b,g,a,d,c,e
//
//        System.out.println("后序遍历");
//        heroTree.postOrder(); // h,f,g,b,d,e,c,a

        heroTree.postFindNo(5);
    }
}
