package Jack2026.OKX;

//Implement the MyLinkedList class:
//
//        MyLinkedList() Initializes the MyLinkedList object.

//        int get(int index) Get the value of the indexth node in the linked list. If the index is invalid, return -1.

//        void addAtHead(int val) Add a node of value val before the first element of the linked list. After the insertion,
//        the new node will be the first node of the linked list.

//        void addAtTail(int val) Append a node of value val as the last element of the linked list.

//        void addAtIndex(int index, int val) Add a node of value val before the indexth node in the linked list. If index
//        equals the length of the linked list, the node will be appended to the end of the linked list. If index is greater
//        than the length, the node will not be inserted.

//        void deleteAtIndex(int index) Delete the indexth node in the linked list, if the index is valid.
public class DesignLinkedList_707 {

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


    public DesignLinkedList_707(){
    }

    public int get(int index)
    {
        if(index < 0 || index >= length) return -1;

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
        {
            return null;
        }

        Node temp = head;
        for(int i = 0; i < index; i++)
        {
            temp = temp.next;
        }
        return temp;
    }

    public void addAtHead(int val){
        Node newNode = new Node(val);
        newNode.next = head;
        head = newNode;

        if(length == 0)
        {
            tail = head;
        }

        length++;
    }

    public void addAtTail(int val)
    {
        if(head == null || length == 0)
        {
            addAtHead(val);
        }
        else {
            tail.next = new Node(val);
            tail = tail.next;
            length++;
        }
    }

    public void addAtIndex(int index, int val)
    {
        if(index < 0 || index > length) return;
        else if(index == 0)
        {
            addAtHead(val);
        }
        else if (index == length)
        {
            addAtTail(val);
        }
        else {
            Node newNode = new Node(val);
            Node prevNode = getNodeAtIndex(index - 1);
            newNode.next = prevNode.next;
            prevNode.next = newNode;
            length++;
        }

    }
    public void deleteAtIndex(int index)
    {
        if(index < 0 || index >= length) return;
        else if (index == 0) {
            head = head.next;
        } else if (index == length - 1) {
            Node prevNode = getNodeAtIndex(index - 1);
            prevNode.next = null;
            tail = prevNode;
        }
        else {
            Node prevNode = getNodeAtIndex(index - 1);
            prevNode.next = prevNode.next.next;
        }
        length--;
    }

}
