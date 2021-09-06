package structure.atguigu.tree.binarytree;

import lombok.Data;

/**
 * @author longyh
 * @Description: 二叉树
 * @analysis: 前序遍历:当前节点、左边、右边
 * 中序遍历：左边，当前节点，右边
 * 后序遍历：左边，右边，当前节点
 * <p>
 * 差别就是，当前节点的输出位置
 * @date 2021/2/14 16:16
 */

/**
 * 分别用三种查询方式，查找no=5的节点
 * 比较查找次数
 */
@Data
public class HeroTree {

    private HeroNode rootNode;

    //前序遍历
    public void preOrder() {
        if (rootNode != null) {
            this.rootNode.preOrder();
        } else {
            System.out.println("树是空的");
        }
    }

    //中序遍历
    public void infixOrder() {
        if (rootNode != null) {
            this.rootNode.infixOrder();
        } else {
            System.out.println("树是空的");
        }
    }

    //后序遍历
    public void postOrder() {
        if (rootNode != null) {
            this.rootNode.postOrder();
        } else {
            System.out.println("树是空的");
        }
    }

    public void preFindNo(int no) {
        if (rootNode != null) {
//            HeroNode heroNode = rootNode.preFindNo(no);
            HeroNode heroNode = rootNode.preOrderSearch_atguigu(no);
            System.out.println("heroNode = " + heroNode);
        }

    }

    public void infixFindNo(int no){
        if (rootNode!=null){
//            HeroNode heroNode = rootNode.infixFindNo(no);
            HeroNode heroNode = rootNode.infixOrderSearch_atguigu(no);
            System.out.println("heroNode = " + heroNode);
        }
    }

    public void postFindNo(int no){
        if (rootNode!=null){
            HeroNode heroNode = rootNode.postOrderSearch_atguigu(no);
            System.out.println("heroNode = " + heroNode);
        }
    }

}
