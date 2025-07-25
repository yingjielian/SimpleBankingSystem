package Jack2025.Karat;
import java.util.*;
public class GenerationGraph_1 {
    public static void main(String[] args) {
        // Sample input
        int[][] connections = {{1, 3}, {2, 3}, {4, 2}, {4, 7}};

        // Process the connections
        processGraph(connections);
    }

    public static void processGraph(int[][] connections) {
        // Maps to track parent-child relationships
        Map<Integer, List<Integer>> parentToChildren = new HashMap<>();
        Map<Integer, List<Integer>> childToParents = new HashMap<>();
        Set<Integer> allNodes = new HashSet<>();

        // Process each connection
        for (int[] connection : connections) {
            int parent = connection[0];
            int child = connection[1];

            allNodes.add(parent);
            allNodes.add(child);

            // Update parent to children map
            parentToChildren.computeIfAbsent(parent, k -> new ArrayList<>()).add(child);

            // Update child to parents map
            childToParents.computeIfAbsent(child, k -> new ArrayList<>()).add(parent);
        }

        // Find parent nodes (nodes that are parents but not children)
        Set<Integer> parentNodes = new HashSet<>();
        for (int node : allNodes) {
            if (!childToParents.containsKey(node)) {
                parentNodes.add(node);
            }
        }

        // Find children with exactly one parent
        List<Integer> childrenWithOneParent = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : childToParents.entrySet()) {
            if (entry.getValue().size() == 1) {
                childrenWithOneParent.add(entry.getKey());
            }
        }

        // Sort the results for consistent output
        List<Integer> sortedParentNodes = new ArrayList<>(parentNodes);
        Collections.sort(sortedParentNodes);

        Collections.sort(childrenWithOneParent);

        // Print the results
        System.out.println("parent - " + sortedParentNodes);
        System.out.println("child with 1 parent - " + childrenWithOneParent);
    }
}
