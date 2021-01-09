package com.saint.base.datastructure;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-09 16:08
 */
public class CommLinkedList<E> {

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
     * 链表头节点
     */
    private Node head;

    /**
     * 链表容量
     */
    int size;

    public CommLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * 在链表头添加元素
     */
    public void addFirst(E e) {
        Node node = new Node(e);
        node.next = head;
        head = node;
        size++;
    }

    /**
     * 在链表尾添加元素
     */
    public void addLast(E e) {
        add(size, e);
    }

    /**
     * 在指定位置添加元素
     */
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("链表下标越界！");
        }
        if (index == 0) {
            addFirst(e);
        } else {
            // 不能在原链表上修改。
            Node pre = head;
            for (int i = 0; i < index - 1; i++) {
                pre = pre.next;
            }
            Node node = new Node(e);
            node.next = pre.next;
            pre.next = node;
            size++;
        }
    }

    /**
     * 移除链表中的指定元素
     */
    public Node remove(E e) {
        while (head != null && head.e == e) {
            head = head.next;
        }
        if (head == null) {
            return head;
        }
        Node pre = head;
        while (pre.next != null) {
            if (pre.next.e == e) {
                pre.next = pre.next.next;
            } else {
                pre = pre.next;
            }
        }
        return head;
    }
}
