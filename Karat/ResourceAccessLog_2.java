package Jack2025.Karat;
import java.util.*;
public class ResourceAccessLog_2 {

    public static String[] mostRequestedResource(String[][] logs) {
        // Step 1: Group access times by resource
        Map<String, List<Integer>> resourceTimesMap = new HashMap<>();

        for (String[] log : logs) {
            int time = Integer.parseInt(log[0]);
            String resource = log[2];
            resourceTimesMap.computeIfAbsent(resource, k -> new ArrayList<>()).add(time);
        }

        // Step 2: Analyze each resource's access pattern
        String maxResource = null;
        int maxAccesses = 0;

        for (Map.Entry<String, List<Integer>> entry : resourceTimesMap.entrySet()) {
            String resource = entry.getKey();
            List<Integer> times = entry.getValue();

            // Sort the times for chronological analysis
            Collections.sort(times);

            // Use sliding window to find max accesses in any 5-minute (300s) window
            int currentMax = 0;
            int left = 0;

            for (int right = 0; right < times.size(); right++) {
                // Move left pointer to maintain 5-minute window
                while (times.get(right) - times.get(left) > 300) {
                    left++;
                }

                // Update current max for this window
                int windowSize = right - left + 1;
                if (windowSize > currentMax) {
                    currentMax = windowSize;
                }
            }

            // Update global max if needed
            if (currentMax > maxAccesses) {
                maxAccesses = currentMax;
                maxResource = resource;
            }
        }

        return new String[]{maxResource, String.valueOf(maxAccesses)};
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

        // Process logs1
        String[] result1 = mostRequestedResource(logs1);
        System.out.println("Most requested resource in logs1: ('" + result1[0] + "', " + result1[1] + ")");

        // Process logs2
        String[] result2 = mostRequestedResource(logs2);
        System.out.println("Most requested resource in logs2: ('" + result2[0] + "', " + result2[1] + ")");
    }
}
