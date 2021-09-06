package structure.atguigu.tree.binarytree;

import lombok.Data;

/**
 * @author longyh
 * @Description: 二叉树节点
 * @analysis:
 * @date 2021/2/14 11:08
 */

@Data
public class HeroNode {

    private int no;
    private String name;
    private HeroNode left;
    private HeroNode right;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);

        if (left != null) {
            this.left.preOrder();
        }

        if (right != null) {
            this.right.preOrder();
        }
    }

    //中序遍历
    public void infixOrder() {
        if (left != null) {
            this.left.infixOrder();
        }

        System.out.println(this);

        if (right != null) {
            this.right.infixOrder();
        }
    }

    //后序遍历
    public void postOrder() {
        if (left != null) {
            this.left.postOrder();
        }

        if (right != null) {
            this.right.postOrder();
        }

        System.out.println(this);
    }

    // 前置查询
    public HeroNode preFindNo(int no) {
        System.out.println("前置查询");
        HeroNode heroNode = null;

        if (this.no == no) {
            heroNode = this;
        }

        if (heroNode == null && this.left != null) {
            heroNode = this.left.preFindNo(no);
        }

        if (heroNode == null && this.right != null) {
            heroNode = this.right.preFindNo(no);
        }

        return heroNode;
    }

    public HeroNode infixFindNo(int no) {
        System.out.println("中置查询");
        HeroNode heroNode = null;
        if (this.left != null) {
            heroNode = this.left.infixFindNo(no);
        }

        if (heroNode == null && this.no == no) {
            heroNode = this;
        }

        if (heroNode == null && this.right != null) {
            heroNode = this.right.infixFindNo(no);
        }

        return heroNode;
    }

    public HeroNode postFindNo(int no) {

        HeroNode heroNode = null;
        if (this.left != null) {
            heroNode = this.left.postFindNo(no);
        }

        if (heroNode == null && this.right != null) {
            heroNode = this.right.postFindNo(no);
        }

        if (heroNode == null && this.no == no) {
            heroNode = this;
        }

        return heroNode;
    }

    public HeroNode preOrderSearch_atguigu(int no) {
        System.out.println("前置查询");
        if (this.no == no) {
            return this;
        }

        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.preOrderSearch_atguigu(no);
        }
        if (resNode != null) {
            return resNode;
        }

        if (this.right != null) {
            resNode = this.right.preOrderSearch_atguigu(no);
        }

        return resNode;
    }

    public HeroNode infixOrderSearch_atguigu(int no) {

        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.infixOrderSearch_atguigu(no);
        }
        if (resNode != null) {
            return resNode;
        }
        System.out.println("中置查询");
        if (this.no == no) {
            return this;
        }

        if (this.right != null) {
            resNode = this.right.infixOrderSearch_atguigu(no);
        }

        return resNode;
    }

    public HeroNode postOrderSearch_atguigu(int no) {

        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.postOrderSearch_atguigu(no);
        }
        if (resNode != null) {
            return resNode;
        }

        if (this.right != null) {
            resNode = this.right.postOrderSearch_atguigu(no);
        }
        if (resNode != null) {
            return resNode;
        }
        System.out.println("后置查询");
        if (this.no == no) {
            return this;
        }

        return resNode;
    }

    public void preOrderDel(int no){
//        preOrderSearch_atguigu(no)
    }
}
