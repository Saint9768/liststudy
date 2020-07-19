package com.saint.base.big.file.sorted;

import java.util.ArrayList;

/**
 * 使用败者树，对已经排序的子文件进行多路归并排序
 * 时间复杂度：O(nlogk),n为总数据量，k为子文件数量
 *
 * @author Saint
 * @version 1.0
 * @date 2020/7/15 17:58
 */
public class FailedTree<T extends Comparable> {

    /**
     * 败者树（有K个节点 --> k为子文件的数量），保存失败下标。tree[0]保存最小值的下标(胜利者)
     */
    private Integer[] tree = null;

    /**
     * 叶子节点数组的个数（即 K路归并中的K）
     */
    private int size = 0;

    /**
     * 叶子节点（必须是可以比较的对象）
     */
    private ArrayList<T> leaves = null;

    /**
     * 初始化最小值
     */
    private static final Integer MIN_KEY = -1;

    /**
     * 失败者树构造函数
     *
     * @param initValues 初始化叶子节点的数组，即各个归并段的首元素组成的数组
     */
    public FailedTree(ArrayList<T> initValues) {
        this.leaves = initValues;
        this.size = initValues.size();
        this.tree = new Integer[size];

        //初始化败者树(其实就是一个普通的二叉树，共有2k - 1个节点)
        for (int i = 0; i < size; i++) {
            //初始化时，树种各个节点值设为最小值
            tree[i] = MIN_KEY;
        }
        //初始化，从最后一个（最小值最大的那个）节点开始调整，k次（小文件个数）调整
        for (int i = size - 1; i >= 0; i--) {
            adjust(i);
        }
    }

    /**
     * 从底向上调整数结构
     *
     * @param s 叶子节点数组的下标(第几个文件)
     */
    private void adjust(int s) {
        // tree[t] 是 leaves[s] 的父节点
        int t = (s + size) / 2;
        while (t > 0) {
            //如果叶子节点值大于父节点（保存的下标）指向的值
            if (s >= 0 && (tree[t] == -1 || leaves.get(s).compareTo(leaves.get(tree[t])) > 0)) {
                //父节点保存其下标：总是保存较大的（败者）。 	较小值的下标（用s记录）->向上传递
                int temp = s;
                s = tree[t];
                tree[t] = temp;
            }
            // tree[Integer/2] 是 tree[Integer] 的父节点
            t /= 2;
        }
        //最后的胜者（最小值）
        tree[0] = s;
    }

    /**
     * 给叶子节点赋值
     *
     * @param leaf 叶子节点值
     * @param s    叶子节点的下标
     */
    public void add(String leaf, int s) {
        leaves.set(s, (T) leaf);
        //每次赋值之后，都要向上调整，使根节点保存最小值的下标（找到当前最小值）
        adjust(s);
    }

    /**
     * 删除叶子节点，即一个归并段元素取空
     *
     * @param s 叶子节点的下标
     */
    public void del(int s) {
        //删除叶子节点
        leaves.remove(s);
        this.size--;
        this.tree = new Integer[size];

        //初始化败者树（严格的说，此时它只是一个普通的二叉树）
        for (int i = 0; i < size; i++) {
            //初始化时，树中各个节点值设为可能的最小值
            tree[i] = MIN_KEY;
        }
        //从最后一个节点开始调整
        for (int i = size - 1; i >= 0; i--) {
            adjust(i);
        }

    }

    /**
     * 根据下标找到叶子节点（取值）
     *
     * @param s 叶子节点下标
     * @return
     */
    public T getLeaf(int s) {
        return leaves.get(s);
    }

    /**
     * 获得胜者(值为最终胜出的叶子节点的下标)
     *
     * @return
     */
    public Integer getWinner() {
        return tree.length > 0 ? tree[0] : null;
    }

    /*public static void main(String[] args) {
        //假设当前有 4 个归并段
        Queue<String> queue0 = new LinkedList();
        Queue<String> queue1 = new LinkedList();
        Queue<String> queue2 = new LinkedList();
        Queue<String> queue3 = new LinkedList();
        String[] source0 = {"c","e","f"};
        String[] source1 = {"b"};
        String[] source2 = {"a"};
        String[] source3 = {"d","g","h"};
        queue0.addAll(Arrays.asList(source0));
        queue1.addAll(Arrays.asList(source1));
        queue2.addAll(Arrays.asList(source2));
        queue3.addAll(Arrays.asList(source3));

        Queue<String>[] sources = new Queue[4];
        sources[0] = queue0;
        sources[1] = queue1;
        sources[2] = queue2;
        sources[3] = queue3;

        //进行 4 路归并
        ArrayList<String> initValues = new ArrayList<>(sources.length);
        for (int i = 0; i < sources.length; i++) {
            initValues.add(sources[i].poll());
        }
        //初始化败者树
        FailedTree<Integer> loserTree = new FailedTree(initValues);
        //输出胜者
        Integer s = loserTree.getWinner();
        System.out.println(loserTree.getLeaf(s) + " ");
        while (sources.length > 0) {
            //新增叶子节点
            String newLeaf = sources[s].poll();
            if (newLeaf == null) {
                //如果多个归并段的长度不一样，这里的逻辑是有问题的。逻辑纠错请看MergeSortedTxt类中。
                // sources[s] 对应的队列（归并段）已经为空，删除队列并调整败者树
                loserTree.del(s);
            } else {
                loserTree.add(newLeaf, s);
            }

            s = loserTree.getWinner();
            if (s == null) {
                break;
            }
            //输出胜者
            System.out.println(loserTree.getLeaf(s) + " ");
        }
    }*/

}