package cn.com.saint.composite;

/**
 * 组合模式
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-08 13:56
 */
public class Test {
    public static void main(String[] args) {
        BranchNode root = new BranchNode("root");
        BranchNode chapter1 = new BranchNode("chapter1");
        BranchNode chapter2 = new BranchNode("chapter2");
        Node left11 = new LeafNode("leftNode11");
        Node left12 = new LeafNode("leftNode12");
        BranchNode branch21 = new BranchNode("branchNode21");
        Node left211 = new LeafNode("leftNode211");
        Node left212 = new LeafNode("leftNode212");
        root.add(chapter1);
        root.add(chapter2);
        chapter1.add(left11);
        chapter1.add(left12);
        chapter2.add(branch21);
        branch21.add(left211);
        branch21.add(left212);
        tree(root, 0);
    }

    private static void tree(Node root, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("---");
        }
        root.p();
        if (root instanceof BranchNode) {
            for (Node node : ((BranchNode) root).nodes) {
                tree(node, depth + 1);
            }
        }
    }
}
