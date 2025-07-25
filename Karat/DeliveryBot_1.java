package Jack2025.Karat;
import java.util.*;
public class DeliveryBot_1 {
    public static Map<String, List<String>> findStartAndEndLocations(List<List<String>> paths) {
        // Build the graph and find all nodes
        Map<String, List<String>> graph = new HashMap<>();
        Set<String> allNodes = new HashSet<>();
        Set<String> potentialStarts = new HashSet<>();
        Set<String> destinations = new HashSet<>();

        // Initialize potential starts with all nodes
        for (List<String> path : paths) {
            String from = path.get(0);
            String to = path.get(1);
            allNodes.add(from);
            allNodes.add(to);
            potentialStarts.add(from);
            destinations.add(to);
        }

        // The real starts are nodes that are not destinations
        Set<String> starts = new HashSet<>(potentialStarts);
        starts.removeAll(destinations);

        // Build the graph
        for (List<String> path : paths) {
            String from = path.get(0);
            String to = path.get(1);
            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(to);
        }

        // For each start, find all reachable end nodes (nodes with no outgoing edges)
        Map<String, List<String>> result = new HashMap<>();
        Set<String> endNodes = new HashSet<>(allNodes);
        endNodes.removeAll(graph.keySet()); // Nodes that are not keys in the graph have no outgoing edges

        for (String start : starts) {
            Set<String> visited = new HashSet<>();
            Queue<String> queue = new LinkedList<>();
            queue.add(start);
            visited.add(start);

            Set<String> reachableEnds = new HashSet<>();

            while (!queue.isEmpty()) {
                String current = queue.poll();

                if (endNodes.contains(current)) {
                    reachableEnds.add(current);
                    continue;
                }

                for (String neighbor : graph.getOrDefault(current, Collections.emptyList())) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }

            if (!reachableEnds.isEmpty()) {
                result.put(start, new ArrayList<>(reachableEnds));
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<List<String>> paths = Arrays.asList(
                Arrays.asList("B", "K"),
                Arrays.asList("C", "K"),
                Arrays.asList("E", "L"),
                Arrays.asList("F", "G"),
                Arrays.asList("J", "M"),
                Arrays.asList("E", "F"),
                Arrays.asList("C", "G"),
                Arrays.asList("A", "B"),
                Arrays.asList("A", "C"),
                Arrays.asList("G", "H"),
                Arrays.asList("G", "I")
        );

        Map<String, List<String>> result = findStartAndEndLocations(paths);
        System.out.println(result);
    }
}
