package com.saint.base.datastructure;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-09 16:09
 */
public class DummyLinkedList<E> {

    /**
     * 链表节点
     */
    private class Node {
        public E e;
        public Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this.e = e;
            this.next = null;
        }
    }

    /**
     * 链表虚拟头节点
     */
    private final Node dummyHead;

    /**
     * 链表容量
     */
    int size;

    public DummyLinkedList() {
        dummyHead = new Node(null, null);
        size = 0;
    }

    /**
     * 在链表的指定位置插入元素e
     */
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("链表下标越界！");
        }
        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
//        Node node = new Node(e);
//        node.next = prev.next;
//        prev.next = node;
        prev.next = new Node(e, prev.next);
        size++;
    }

    /**
     * 删除链表中value值为e的元素
     */
    public void remove(E e) {
        Node pre = dummyHead;
        while (pre.next != null) {
            if (pre.next.e.equals(e)) {
                Node delNode = pre.next;
                pre.next = pre.next.next;
                // 释放内存
                delNode.next = null;
            } else {
                pre = pre.next;
            }
        }
    }
}
