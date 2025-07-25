package Jack2025.Karat;
import java.util.*;
public class ResourceAccessLog_1 {
    public static Map<String, List<String>> getUserAccessTimes(String[][] logs) {
        Map<String, List<Integer>> userTimesMap = new HashMap<>();

        // Process each log entry
        for (String[] log : logs) {
            String timeStr = log[0];
            String user = log[1];
            int time = Integer.parseInt(timeStr);

            // Add time to user's time list
            userTimesMap.computeIfAbsent(user, k -> new ArrayList<>()).add(time);
        }

        // Prepare the result map
        Map<String, List<String>> result = new TreeMap<>();

        // For each user, find min and max times
        for (Map.Entry<String, List<Integer>> entry : userTimesMap.entrySet()) {
            String user = entry.getKey();
            List<Integer> times = entry.getValue();

            // Find min and max
            int min = Collections.min(times);
            int max = Collections.max(times);

            // Add to result map
            result.put(user, Arrays.asList(String.valueOf(min), String.valueOf(max)));
        }

        return result;
    }

    public static void main(String[] args) {
        // Example 1
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

        // Example 2
        String[][] logs2 = {
                {"300", "user_1", "resource_3"},
                {"599", "user_1", "resource_3"},
                {"900", "user_1", "resource_3"},
                {"1199", "user_1", "resource_3"},
                {"1200", "user_1", "resource_3"},
                {"1201", "user_1", "resource_3"},
                {"1202", "user_1", "resource_3"}
        };

        // Process logs1
        System.out.println("Processing logs1:");
        Map<String, List<String>> result1 = getUserAccessTimes(logs1);
        for (Map.Entry<String, List<String>> entry : result1.entrySet()) {
            System.out.println(entry.getKey() + ":[" + entry.getValue().get(0) + "," + entry.getValue().get(1) + "]");
        }

        // Process logs2
        System.out.println("\nProcessing logs2:");
        Map<String, List<String>> result2 = getUserAccessTimes(logs2);
        for (Map.Entry<String, List<String>> entry : result2.entrySet()) {
            System.out.println(entry.getKey() + ":[" + entry.getValue().get(0) + "," + entry.getValue().get(1) + "]");
        }
    }
}
