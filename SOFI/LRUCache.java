package Jack2025.SOFI;

import java.util.*;

public class LRUCache {
    class Node {
        int key;
        int value;
        Node next;
        Node pre;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private HashMap<Integer, Node> map;
    private int capacity;
    // 整个链表的头部，也就是最老的元素
    private Node head;
    // 整个链表的尾部，也就是最新的元素
    private Node tail;

    public LRUCache(int capacity) {
        map = new HashMap<>();
        this.capacity = capacity;
        head = null;
        tail = null;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        // 如果node已经是最新的元素了，那就可以直接返回了
        if (node != tail) {
            // 如果node是head，需要把它移到末尾
            if (node == head) {
                head = head.next;
            } else {
                // node不是head
                // 要把它取出来，然后把它前边和后边的点连在一起
                node.pre.next = node.next;
                node.next.pre = node.pre;
            }
            // 把这个点放到最后
            tail.next = node;
            node.pre = tail;
            node.next = null;
            tail = node;
        }

        return node.value;
    }

    public void put(int key, int value) {
        Node node = map.get(key);
        // 已经存在这个node
        // 只需要更新node的值
        if (node != null) {
            node.value = value;
            if (node != tail) {
                if (node == head) {
                    head = head.next;
                } else {
                    node.pre.next = node.next;
                    node.next.pre = node.pre;
                }

                tail.next = node;
                node.pre = tail;
                node.next = null;
                tail = node;
            }
        } else {
            // 需要插入新的节点了
            Node newNode = new Node(key, value);
            if (capacity == 0) {
                map.remove(head.key);
                head = head.next;
                // 后边会减少容量
                capacity++;
            }
            // 如果是链表中的第一个点
            if (head == null) {
                head = newNode;
            } else {
                tail.next = newNode;
                newNode.pre = tail;
                newNode.next = null;
            }

            tail = newNode;
            map.put(key, newNode);
            // 减少一个容量
            capacity--;
        }
    }
}
