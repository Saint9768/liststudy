package cn.com.saint.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 树枝节点
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-10-08 13:53
 */
public class BranchNode extends Node {

    List<Node> nodes = new ArrayList<>();

    private final String name;

    public BranchNode(String name) {
        this.name = name;
    }

    public void add(Node node) {
        nodes.add(node);
    }

    @Override
    void p() {
        System.out.println(name);
    }
}
