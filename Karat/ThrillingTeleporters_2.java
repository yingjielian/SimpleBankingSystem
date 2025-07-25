package Jack2025.Karat;
import java.util.*;
public class ThrillingTeleporters_2 {
    public static boolean finishable(String[] teleporters, int dieSides, int startPos, int lastSquare) {
        // Create a map to store teleporters for quick lookup
        Map<Integer, Integer> teleporterMap = new HashMap<>();
        for (String teleporter : teleporters) {
            String[] parts = teleporter.split(",");
            int from = Integer.parseInt(parts[0]);
            int to = Integer.parseInt(parts[1]);
            teleporterMap.put(from, to);
        }

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(startPos);
        visited.add(startPos);

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int roll = 1; roll <= dieSides; roll++) {
                int newPos = current + roll;

                // Check if we've reached or passed the last square
                if (newPos >= lastSquare) {
                    return true;
                }

                // Check if this square has a teleporter
                if (teleporterMap.containsKey(newPos)) {
                    newPos = teleporterMap.get(newPos);
                }

                if (!visited.contains(newPos)) {
                    visited.add(newPos);
                    queue.add(newPos);
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        String[] teleporters1 = {"10,8", "11,5", "12,7", "13,9"};
        System.out.println(finishable(teleporters1, 4, 0, 20)); // false

        String[] teleporters2 = {"10,8", "11,5", "12,7", "13,9", "2,15"};
        System.out.println(finishable(teleporters2, 4, 0, 20)); // true
        System.out.println(finishable(teleporters2, 4, 9, 20)); // false

        String[] teleporters3 = {"10,8", "11,5", "12,1", "13,9", "2,15"};
        System.out.println(finishable(teleporters3, 4, 9, 20)); // true

        String[] teleporters4 = {"2,4", "9,8", "11,7", "12,6", "18,14",
                "19,16", "20,9", "21,14", "22,6", "23,26",
                "25,10", "28,19", "29,27", "31,29", "38,33",
                "39,17", "41,30", "42,28", "45,44", "46,36"};
        System.out.println(finishable(teleporters4, 4, 0, 50)); // false
        System.out.println(finishable(teleporters4, 6, 0, 50)); // true

        String[] teleporters5 = {"4,21", "11,18", "13,17", "16,17", "18,21",
                "22,11", "26,25", "27,9", "31,38", "32,43",
                "34,19", "35,19", "36,39", "38,25", "41,31"};
        System.out.println(finishable(teleporters5, 4, 0, 50)); // true
        System.out.println(finishable(teleporters5, 2, 0, 50)); // false
    }
}
