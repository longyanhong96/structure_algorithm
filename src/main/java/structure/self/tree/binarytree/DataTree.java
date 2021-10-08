package structure.self.tree.binarytree;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author longyh
 * @Description:
 * @analysis: 前序遍历:当前节点、左边、右边
 * 中序遍历：左边，当前节点，右边
 * 后序遍历：左边，右边，当前节点
 * @date 2021/10/8 5:31 下午
 */
@Data
public class DataTree<T> implements Tree {

    private TreeNode<T> root;

    @Override
    public void preOrder() {
        if (ObjectUtils.anyNotNull(root)) {

        } else {
            throw new NullPointerException("tree is null");
        }
    }

    @Override
    public void infixOrder() {

    }

    @Override
    public void postOrder() {

    }


    private class TreeNode<T> {
        private T self;
        private TreeNode<T> leftTreeNode;
        private TreeNode<T> rightTreeNode;


        /**
         * 前序遍历
         */
        private void preOrder() {
            println();
            if (leftTreeNode != null) {
                leftTreeNode.preOrder();
            }

            if (rightTreeNode != null) {
                rightTreeNode.preOrder();
            }
        }

        /**
         * 中序遍历
         */
        private void infixOrder() {
            if (leftTreeNode != null) {
                leftTreeNode.infixOrder();
            }
            println();

            if (rightTreeNode != null) {
                rightTreeNode.infixOrder();
            }
        }

        /**
         * 后序遍历
         */
        private void postOrder() {
            if (leftTreeNode != null) {
                leftTreeNode.postOrder();
            }

            if (rightTreeNode != null) {
                rightTreeNode.postOrder();
            }
            println();
        }

        private void println() {
            System.out.println(self);
        }
    }
}
