package Jack2025.Meta;

import java.util.*;
public class CountOnes {

    private int[] prefix;

    // Constructor: preprocess prefix sums
    public CountOnes(int[] nums) {
        int n = nums.length;
        prefix = new int[n];
        prefix[0] = (nums[0] == 1 ? 1 : 0);
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + (nums[i] == 1 ? 1 : 0);
        }
    }

    // Query number of 1s in [start, end] inclusive
    public int query(int start, int end) {
        if (start == 0) return prefix[end];
        return prefix[end] - prefix[start - 1];
    }

    // Example usage
    public static void main(String[] args) {
        int[] arr1 = {0, 0, 1, 0, 1, 0};
        CountOnes counter1 = new CountOnes(arr1);
        System.out.println(counter1.query(1, 5)); // 1
        System.out.println(counter1.query(2, 4)); // 2

        int[] arr2 = {1, 1, 1, 1, 1, 1};
        CountOnes counter2 = new CountOnes(arr2);
        System.out.println(counter2.query(0, 5)); // 6
        System.out.println(counter2.query(2, 4)); // 3
    }
}
