package Jack2025.Karat;
import java.util.*;
public class GenerationGraph_2 {
    public static void main(String[] args) {
        // First test case
        int[][] parentChildPairs1 = {
                {1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7}, {4, 5}, {4, 8}, {4, 9},
                {9, 11}, {14, 4}, {13, 12}, {12, 9}, {15, 13}
        };

        // Test cases for first example
        System.out.println(hasCommonAncestor(parentChildPairs1, 3, 8)); // false
        System.out.println(hasCommonAncestor(parentChildPairs1, 5, 8)); // true
        System.out.println(hasCommonAncestor(parentChildPairs1, 6, 8)); // true
        System.out.println(hasCommonAncestor(parentChildPairs1, 6, 9)); // true
        System.out.println(hasCommonAncestor(parentChildPairs1, 1, 3)); // false
        System.out.println(hasCommonAncestor(parentChildPairs1, 3, 1)); // false
        System.out.println(hasCommonAncestor(parentChildPairs1, 7, 11)); // true
        System.out.println(hasCommonAncestor(parentChildPairs1, 6, 5)); // true
        System.out.println(hasCommonAncestor(parentChildPairs1, 5, 6)); // true

        // Second test case
        int[][] parentChildPairs2 = {
                {1, 3}, {11, 10}, {11, 12}, {2, 3}, {10, 2},
                {10, 5}, {3, 4}, {5, 6}, {5, 7}, {7, 8}
        };

        // Test cases for second example
        System.out.println(hasCommonAncestor(parentChildPairs2, 4, 12)); // true
        System.out.println(hasCommonAncestor(parentChildPairs2, 1, 6)); // false
        System.out.println(hasCommonAncestor(parentChildPairs2, 1, 12)); // false
    }

    public static boolean hasCommonAncestor(int[][] parentChildPairs, int node1, int node2) {
        // Get all ancestors for both nodes
        Set<Integer> ancestors1 = getAllAncestors(parentChildPairs, node1);
        Set<Integer> ancestors2 = getAllAncestors(parentChildPairs, node2);

        // Check for intersection (common ancestors)
        for (int ancestor : ancestors1) {
            if (ancestors2.contains(ancestor)) {
                return true;
            }
        }

        return false;
    }

    private static Set<Integer> getAllAncestors(int[][] parentChildPairs, int node) {
        Set<Integer> ancestors = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(node);

        // Build a child to parents map
        Map<Integer, List<Integer>> childToParents = new HashMap<>();
        for (int[] pair : parentChildPairs) {
            childToParents.computeIfAbsent(pair[1], k -> new ArrayList<>()).add(pair[0]);
        }

        // BFS to find all ancestors
        while (!queue.isEmpty()) {
            int current = queue.poll();
            List<Integer> parents = childToParents.getOrDefault(current, new ArrayList<>());

            for (int parent : parents) {
                if (!ancestors.contains(parent)) {
                    ancestors.add(parent);
                    queue.add(parent);
                }
            }
        }

        return ancestors;
    }
}
