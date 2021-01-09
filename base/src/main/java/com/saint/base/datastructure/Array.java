package com.saint.base.datastructure;

import org.omg.CORBA.Object;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-09 15:47
 */
public class Array<E> {

    private E[] data;

    private int size;

    public Array(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    public Array() {
        this(10);
    }

    /**
     * 在index索引处插入e
     */
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("数组下标越界!");
        }
        // 扩容
        if (size == data.length) {
            resize(2 * data.length);
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = e;
        size++;
    }

    /**
     * 删除index索引处的数据
     */
    public E remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("数组下标越界!");
        }
        E res = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        data[size] = null;
        // 缩容
        if (size == data.length / 4 && data.length > 1) {
            resize(data.length / 2);
        }
        return res;
    }

    /**
     * 删除所有value为e的数据
     */
    public int removeElement(E e) {
        List<Integer> indexList = findAll(e);
        int res = 0;
        for (int index : indexList) {
            remove(index);
            res++;
        }
        return res;
    }

    /**
     * 找到所有值为E的数据索引
     */
    public List<Integer> findAll(E e) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * Array中是否包含值为e的元素
     */
    public boolean isContain(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return this.size;
    }

    public int getCapacity() {
        return data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 数组的动态扩容
     *
     * @param newCapacity 新容量
     */
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }
}
