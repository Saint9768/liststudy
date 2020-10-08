package cn.com.saint.composite;

/**
 * 叶子节点
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-08 13:53
 */
public class LeafNode extends Node {

    private final String content;

    public LeafNode(String content) {
        this.content = content;
    }

    @Override
    void p() {
        System.out.println(content);
    }
}
