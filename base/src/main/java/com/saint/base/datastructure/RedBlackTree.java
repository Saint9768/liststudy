package com.saint.base.datastructure;

/**
 * 红黑树数据结构
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-09 13:16
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    public static void main(String[] args) {
        RedBlackTree rdTree = new RedBlackTree();
        rdTree.add(1, 1);
        rdTree.add(3, 1);
        rdTree.add(2, 1);
        RedBlackTree.TreeNode root = rdTree.root;
        System.out.println(root);
    }

    /**
     * 判断节点指向父节点的连线的颜色 Red<-->true, black<-->false
     */
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /**
     * 树节点
     */
    class TreeNode {
        public K key;
        public V value;
        public TreeNode left;
        public TreeNode right;
        public boolean color;

        public TreeNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.color = RED;
        }
    }

    private TreeNode root;

    private int size;

    public RedBlackTree() {
        root = null;
        size = 0;
    }

    /**
     * 判断一个树节点是否是红节点。
     *
     * @param node 树节点
     */
    public boolean isRed(TreeNode node) {
        if (node == null) {
            return BLACK;
        }
        return node.color;
    }

    public void add(K key, V value) {
        root = add(root, key, value);
        root.color = BLACK;
    }

    public TreeNode add(TreeNode node, K key, V value) {
        if (node == null) {
            size++;
            return new TreeNode(key, value);
        }
        // 1.往树中添加节点
        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else {
            node.value = value;
        }
        // 2.维护红黑树的结构
        // 2.1 如果node节点的右子节点为红色的，而左子节点不是红色的，左旋转
        if (isRed(node.right) && !isRed(node.left)) {
            node = leftRotate(node);
        }
        // 2.2 如果node节点的左子节点、左子节点的左子节点是红色的，右旋转
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }
        // 2.3 如果node节点的左子节点和右子节点都是红节点，则颜色翻转
        if (isRed(node.left) && isRed(node.right) && !isRed(node)) {
            flipColor(node);
        }
        return node;
    }


    /**
     * 左旋转
     * node                      x
     * /  \         左旋转       /  \
     * T1  x      --------->  node  T3
     * / \                    / \
     * T2 T3                 T1  T2
     */
    private TreeNode leftRotate(TreeNode node) {
        TreeNode x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    /**
     * 右旋转
     * node                          x
     * /  \         右旋转          /  \
     * x   T3      --------->    T1   node
     * / \                            /  \
     * T1 T2                         T2  T3
     */
    private TreeNode rightRotate(TreeNode node) {
        TreeNode x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    /**
     * 颜色翻转
     * 如果一个节点的颜色为黑色，而它左右子节点的颜色为红色。
     * 则将其的颜色置为红色，其子节点的颜色置为黑色。
     */
    private void flipColor(TreeNode node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }
}