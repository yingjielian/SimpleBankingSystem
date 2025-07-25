package Jack2025.Karat;
import java.util.*;
public class GenerationGraph_3 {
    public static void main(String[] args) {
        // Test case 1
        int[][] pairs1 = {
                {2, 3}, {3, 15}, {3, 6}, {5, 6}, {5, 7},
                {4, 5}, {4, 8}, {4, 9}, {9, 11}, {14, 4}
        };

        // Test cases
        System.out.println(findEarliestAncestor(pairs1, 8)); // 14
        System.out.println(findEarliestAncestor(pairs1, 7)); // 14
        System.out.println(findEarliestAncestor(pairs1, 6)); // 14
        System.out.println(findEarliestAncestor(pairs1, 15)); // 2
        System.out.println(findEarliestAncestor(pairs1, 14)); // -1
        System.out.println(findEarliestAncestor(pairs1, 11)); // 14
    }

    public static Integer findEarliestAncestor(int[][] parentChildPairs, int node) {
        // Build child to parents map
        Map<Integer, List<Integer>> childToParents = new HashMap<>();
        for (int[] pair : parentChildPairs) {
            childToParents.computeIfAbsent(pair[1], k -> new ArrayList<>()).add(pair[0]);
        }

        // If the node has no parents, return -1 (root node)
        if (!childToParents.containsKey(node)) {
            return -1;
        }

        // Perform BFS to find the earliest ancestor
        Queue<Integer> queue = new LinkedList<>();
        queue.add(node);
        Set<Integer> visited = new HashSet<>();
        Integer earliestAncestor = null;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            visited.add(current);

            List<Integer> parents = childToParents.getOrDefault(current, new ArrayList<>());

            for (int parent : parents) {
                if (!visited.contains(parent)) {
                    queue.add(parent);
                    // The last node we visit in BFS will be the earliest ancestor
                    earliestAncestor = parent;
                }
            }
        }

        return earliestAncestor;
    }
}
