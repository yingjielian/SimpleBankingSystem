package Jack2026.Nebius;

import java.util.HashMap;
import java.util.Map;

class LRUCache{
    class Node{
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value)
        {
            this.key = key;
            this.value = value;
        }
    }

    int capacity;
    Map<Integer, Node> map;
    Node head;
    Node tail;

    LRUCache(int capacity)
    {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    public int get(int key)
    {
        Node node = map.get(key);
        if(node == null)
        {
            return -1;
        }
        updateCache(node);

        return node.value;
    }

    public void updateCache(Node node)
    {
        if(node != tail)
        {
            if(node == head)
            {
                head = head.next;
            }
            else
            {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            tail.next = node;
            node.next = null;
            node.prev = tail;
            tail = node;
        }
    }

    public void put(int key, int value)
    {
        Node node = map.get(key);

        if(node != null)
        {
            node.value = value;
            updateCache(node);
        }
        else
        {
            Node newNode = new Node(key, value);
            if(capacity == 0)
            {
                map.remove(head.key);
                head = head.next;
                capacity++;
            }

            if(head == null)
            {
                head = newNode;
            }
            else
            {
                tail.next = newNode;
                newNode.next = null;
                newNode.prev = tail;
            }
            tail = newNode;
            map.put(key, newNode);
            capacity--;
        }
    }
}