package Jack2025.LinkedIn;
import java.util.*;

public class LRUCache_146 {
    class Node{
        int key;
        int value;
        Node next;
        Node pre;

        public Node(int key, int value)
        {
            this.key = key;
            this.value = value;
        }
    }

    private HashMap<Integer, Node> map;
    private int capacity;
    private Node head;
    private Node tail;

    public LRUCache_146(int capacity)
    {
        map = new HashMap<>();
        this.capacity = capacity;
        head = null;
        tail = null;
    }

    public int get(int key)
    {
        Node node = map.get(key);
        if(node == null)
        {
            return -1;
        }

        if(node != tail)
        {
            if(node == head)
            {
                head = head.next;
            }
            else {
                node.pre.next = node.next;
                node.next.pre = node.pre;
            }
            tail.next = node;
            node.pre = tail;
            node.next = null;
            tail = node;
        }
        return node.value;
    }

    public void put(int key, int value)
    {
        Node node = map.get(key);

        if(node != null)
        {
            node.value = value;
            if(node != tail)
            {
                if(node == head)
                {
                    head = head.next;
                }
                else {
                    node.pre.next = node.next;
                    node.next.pre = node.pre;
                }

                tail.next = node;
                node.pre = tail;
                node.next = null;
                tail = node;
            }
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
            else{
                tail.next = newNode;
                newNode.pre = tail;
                newNode.next = null;
            }
            tail = newNode;
            map.put(key, newNode);
            capacity--;
        }
    }
}
