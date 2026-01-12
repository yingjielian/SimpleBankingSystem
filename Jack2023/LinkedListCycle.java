package Jack2023;

import java.util.HashSet;
import java.util.Stack;

public class LinkedListCycle {

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        a.next = new ListNode(2);
        a.next.next = new ListNode(3);
        a.next.next.next = new ListNode(4);

        System.out.println(hasCycle(a));
    }
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }
    public static boolean hasCycle(ListNode head)
    {
        HashSet<ListNode> stack = new HashSet<>();

        while(head != null)
        {
            if(!stack.add(head))
            {
                return true;
            }
            head = head.next;
        }
        return false;
    }
}
