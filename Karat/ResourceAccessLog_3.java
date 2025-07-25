package Jack2025.Karat;
import java.util.*;
public class ResourceAccessLog_3 {
    public static Map<String, Map<String, Double>> buildTransitionGraph(String[][] logs) {
        // Step 1: Group logs by user and sort each user's accesses by time
        Map<String, List<LogEntry>> userSessions = new HashMap<>();

        for (String[] log : logs) {
            int time = Integer.parseInt(log[0]);
            String user = log[1];
            String resource = log[2];
            userSessions.computeIfAbsent(user, k -> new ArrayList<>())
                    .add(new LogEntry(time, resource));
        }

        // Sort each user's accesses by time
        for (List<LogEntry> entries : userSessions.values()) {
            entries.sort(Comparator.comparingInt(LogEntry::getTime));
        }

        // Step 2: Build transition counts
        Map<String, Map<String, Integer>> transitionCounts = new HashMap<>();
        Map<String, Integer> startCounts = new HashMap<>();

        // Add START and END to transition counts
        transitionCounts.put("START", new HashMap<>());

        for (List<LogEntry> entries : userSessions.values()) {
            if (entries.isEmpty()) continue;

            // Process START transitions
            String firstResource = entries.get(0).getResource();
            startCounts.put(firstResource, startCounts.getOrDefault(firstResource, 0) + 1);

            // Process intermediate transitions
            for (int i = 0; i < entries.size(); i++) {
                String current = entries.get(i).getResource();
                String next = (i + 1 < entries.size()) ? entries.get(i + 1).getResource() : "END";

                transitionCounts.computeIfAbsent(current, k -> new HashMap<>())
                        .put(next, transitionCounts.get(current).getOrDefault(next, 0) + 1);
            }
        }

        // Step 3: Convert counts to probabilities
        Map<String, Map<String, Double>> transitionGraph = new HashMap<>();

        // Process START probabilities
        int totalStarts = startCounts.values().stream().mapToInt(Integer::intValue).sum();
        Map<String, Double> startProbs = new HashMap<>();
        for (Map.Entry<String, Integer> entry : startCounts.entrySet()) {
            startProbs.put(entry.getKey(), (double) entry.getValue() / totalStarts);
        }
        transitionGraph.put("START", startProbs);

        // Process other resource probabilities
        for (Map.Entry<String, Map<String, Integer>> entry : transitionCounts.entrySet()) {
            String resource = entry.getKey();
            if (resource.equals("START")) continue;

            Map<String, Integer> counts = entry.getValue();
            int totalTransitions = counts.values().stream().mapToInt(Integer::intValue).sum();
            Map<String, Double> probs = new HashMap<>();

            for (Map.Entry<String, Integer> transition : counts.entrySet()) {
                probs.put(transition.getKey(), (double) transition.getValue() / totalTransitions);
            }

            transitionGraph.put(resource, probs);
        }

        return transitionGraph;
    }

    public static void main(String[] args) {
        // Example logs1
        String[][] logs1 = {
                {"58523", "user_1", "resource_1"},
                {"62314", "user_2", "resource_2"},
                {"54001", "user_1", "resource_3"},
                {"200", "user_6", "resource_5"},
                {"215", "user_6", "resource_4"},
                {"54060", "user_2", "resource_3"},
                {"53760", "user_3", "resource_3"},
                {"58522", "user_22", "resource_1"},
                {"53651", "user_5", "resource_3"},
                {"2", "user_6", "resource_1"},
                {"100", "user_6", "resource_6"},
                {"400", "user_7", "resource_2"},
                {"100", "user_8", "resource_6"},
                {"54359", "user_1", "resource_3"}
        };

        // Example logs2
        String[][] logs2 = {
                {"300", "user_1", "resource_3"},
                {"599", "user_1", "resource_3"},
                {"900", "user_1", "resource_3"},
                {"1199", "user_1", "resource_3"},
                {"1200", "user_1", "resource_3"},
                {"1201", "user_1", "resource_3"},
                {"1202", "user_1", "resource_3"}
        };

        // Build transition graph for logs1
        System.out.println("Transition graph for logs1:");
        Map<String, Map<String, Double>> graph1 = buildTransitionGraph(logs1);
        printGraph(graph1);

        // Build transition graph for logs2
        System.out.println("\nTransition graph for logs2:");
        Map<String, Map<String, Double>> graph2 = buildTransitionGraph(logs2);
        printGraph(graph2);
    }

    private static void printGraph(Map<String, Map<String, Double>> graph) {
        for (Map.Entry<String, Map<String, Double>> entry : graph.entrySet()) {
            System.out.print("'" + entry.getKey() + "': {");
            boolean first = true;
            for (Map.Entry<String, Double> transition : entry.getValue().entrySet()) {
                if (!first) System.out.print(", ");
                System.out.print("'" + transition.getKey() + "': " + transition.getValue());
                first = false;
            }
            System.out.println("}");
        }
    }

    static class LogEntry {
        private int time;
        private String resource;

        public LogEntry(int time, String resource) {
            this.time = time;
            this.resource = resource;
        }

        public int getTime() { return time; }
        public String getResource() { return resource; }
    }

}
