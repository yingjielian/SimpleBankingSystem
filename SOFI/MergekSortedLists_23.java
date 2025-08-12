package Jack2025.SOFI;


/*This code merges k sorted linked lists into one sorted list by iteratively merging them two at a time.
The mergeTwoLists method uses a dummy node and pointer to efficiently merge two sorted lists by always attaching the
 smaller current node first.
Once all lists are merged into lists[0], the final merged linked list is returned.*/
public class MergekSortedLists_23 {

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length == 0){
            return null;
        }

        for(int i = 1; i < lists.length; i++)
        {
            lists[0] = mergeTwoLists(lists[0], lists[i]);
        }

        return lists[0];


    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }

        if(l1 == null){
            curr.next = l2;
        }

        if(l2 == null){
            curr.next = l1;
        }

        return dummy.next;
    }
}
