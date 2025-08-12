package Jack2025.SOFI;

import java.util.*;

/*This code calculates the shortest subarray that has the same degree as the entire array.
It uses a HashMap to store each number's frequency (count) and its first occurrence index (start) in a custom Node
object.
While iterating through the array, it updates the degree (maximum frequency) and keeps track of the smallest subarray
  length that achieves this degree.*/
public class ArrayDegree_697 {
    class Node {
        int count;
        int start;

        Node(int count, int start) {
            this.count = count;
            this.start = start;
        }
    }

    public int findShortestSubArray(int[] nums) {
        HashMap<Integer, Node> map = new HashMap<>();

        int res = 0;
        int degree = 0;

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];

            if (!map.containsKey(num)) {
                map.put(num, new Node(1, i));
            } else {
                Node node = map.get(num);
                node.count++;
                map.put(num, node);
            }

            Node node = map.get(num);
            if (node.count > degree) {
                degree = node.count;
                res = i - node.start + 1;
            } else if (node.count == degree) {
                res = Math.min(res, i - node.start + 1);
            }
        }

        return res;
    }
}
