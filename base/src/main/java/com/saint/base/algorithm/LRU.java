package com.saint.base.algorithm;

import java.util.HashMap;

/**
 * @author Saint
 * @createTime 2020-08-04 13:25
 */
public class LRU<K, V> {
    /**
     * 可以使用双向链表和哈希表实现一个LRU缓存
     * 1、使用双向链表实现的队列，队列的最大容量是缓存的大小。在使用的过程中，把最近使用的页面移动到队列头，最近没有使用的放到队尾
     * 2、使用一个哈希表，把页号作为键，把缓存在队列中的节点的的地址作为值
     * 当引用一个页面时，这个所需的页面在内存中，需要把这个页对应的节点移动到队列的前面，并且更新在哈希表中结点的地址
     * 当所需的页面不在内存时，我们把它存储到内存中
     * 当队列满时，那么就从队列尾部移除一个结点，并将新结点添加到队列的前面
     */

    /**
     * 存储键值的节点
     */
    class Entry<K, V> {
        public Entry pre;
        public Entry next;
        public K key;
        public V value;
    }

    private final int MAX_CACHE_SIZE;
    private Entry first;
    private Entry last;

    private HashMap<K, Entry<K, V>> hashMap;

    /**
     * @param cacheSize 初始的缓存的大小
     */
    public LRU(int cacheSize) {
        this.MAX_CACHE_SIZE = cacheSize;
        hashMap = new HashMap<>();
    }

    public void put(K key, V value) {
        Entry entry = getEntry(key);
        if (entry == null) {
            if (hashMap.size() >= MAX_CACHE_SIZE) {
                hashMap.remove(last.key);
                removeLast();
            }
            entry = new Entry();
            entry.key = key;
        }
        entry.value = value;
        moveToFirst(entry);
        hashMap.put(key, entry);
    }


    /**
     * 获取key键对应的值
     *
     * @param key
     * @return
     */
    public V get(K key) {
        Entry<K, V> entry = getEntry(key);
        if (entry == null) {
            return null;
        }
        // 将当前entry移动到开头
        moveToFirst(entry);
        return entry.value;
    }

    /**
     * 将最近访问的entry移动到开头
     *
     * @param entry
     */
    private void moveToFirst(Entry entry) {
        /**
         * 1、如果是第一个元素，则不需要移动
         */
        if (entry == first) {
            return;
        }

        /**
         * 2、如果entry节点的前面的节点存在，则改变前面的节点的指向
         */
        if (entry.pre != null) {
            entry.pre.next = entry.next;
        }

        /**
         * 3、如果entry节点的后面存在，则改变后面节点的前向指向
         */
        if (entry.next != null) {
            entry.next.pre = entry.pre;
        }

        /**
         * 4、如果当前结点是最后一个结点，那么该变last指向
         */
        if (entry == last) {
            last = last.pre;
        }

        /**
         * 5、移动entry到队头
         */
        if (first == null || last == null) {
            first = last = entry;
            return;
        }
        entry.next = first;
        first.pre = entry;
        entry.pre = null;
    }

    /**
     * 删除
     */
    private void removeLast() {
        if (last != null) {
            last = last.pre;
            if (last == null) {
                first = null;
            } else {
                last.next = null;
            }
        }
    }

    private Entry<K, V> getEntry(K key) {
        return hashMap.get(key);
    }

    public static void main(String[] args) {
        LRU<Integer, String> lru = new LRU<>(5);
        lru.put(1, "String1");
        lru.put(2, "String2");
        lru.put(3, "String3");
        lru.put(4, "String4");
        lru.put(5, "String5");
        lru.put(6, "String6");
        System.out.println(lru.get(1));
        System.out.println(lru.get(2));
    }
}
