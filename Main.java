package Jack2025;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Main {

    public static void main(String[] args)
    {
//        String a = new String("hello");
//        String b = new String("hello");
//        System.out.println(a == b);
//        System.out.println(a.equals(b));

        System.out.println((char)(2 + '0'));
    }

    private static class Node{
        int val;
        Node next;
        public Node(int val)
        {
            this.val = val;
        }
    }

    Node head = null;
    Node tail = null;
    int length = 0;

    public int get(int index)
    {
        if(index < 0 || index >= length)
            return -1;

        Node temp = head;
        for(int i = 0; i < index; i++)
        {
            temp = temp.next;
        }
        return temp.val;
    }

    private Node getNodeAtIndex(int index)
    {
        if(index < 0 || index >= length)
            return null;

        Node temp = head;
        for(int i = 0; i < index; i++)
        {
            temp = temp.next;
        }
        return temp;
    }

}
