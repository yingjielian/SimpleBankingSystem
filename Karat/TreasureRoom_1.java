package Jack2025.Karat;
import java.util.*;
public class TreasureRoom_1 {

    public static List<String> filter_rooms(List<String> treasure_rooms, List<List<String>> instructions) {
        Map<String, Integer> destinationCount = new HashMap<>();
        Map<String, String> roomToDestination = new HashMap<>();
        Set<String> treasureSet = new HashSet<>(treasure_rooms);

        // Process each instruction to populate destinationCount and roomToDestination
        for (List<String> instruction : instructions) {
            String source = instruction.get(0);
            String destination = instruction.get(1);
            roomToDestination.put(source, destination);
            destinationCount.put(destination, destinationCount.getOrDefault(destination, 0) + 1);
        }

        List<String> result = new ArrayList<>();

        // Check each room in roomToDestination's keys to see if it meets the conditions
        for (String room : roomToDestination.keySet()) {
            int incomingLinks = destinationCount.getOrDefault(room, 0);
            if (incomingLinks >= 2) {
                String nextRoom = roomToDestination.get(room);
                if (treasureSet.contains(nextRoom)) {
                    result.add(room);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // Test case 1
        List<List<String>> instructions1 = Arrays.asList(
                Arrays.asList("jasmin", "tulip"),
                Arrays.asList("lily", "tulip"),
                Arrays.asList("tulip", "tulip"),
                Arrays.asList("rose", "rose"),
                Arrays.asList("violet", "rose"),
                Arrays.asList("sunflower", "violet"),
                Arrays.asList("daisy", "violet"),
                Arrays.asList("iris", "violet")
        );
        List<String> treasureRooms1 = Arrays.asList("lily", "tulip", "violet", "rose");
        System.out.println(filter_rooms(treasureRooms1, instructions1)); // Expected: ["tulip", "violet"]

        // Test case 2
        List<String> treasureRooms2 = Arrays.asList("lily", "jasmin", "violet");
        System.out.println(filter_rooms(treasureRooms2, instructions1)); // Expected: []

        // Test case 3
        List<List<String>> instructions2 = Arrays.asList(
                Arrays.asList("jasmin", "tulip"),
                Arrays.asList("lily", "tulip"),
                Arrays.asList("tulip", "violet"),
                Arrays.asList("violet", "violet")
        );
        List<String> treasureRooms3 = Collections.singletonList("violet");
        System.out.println(filter_rooms(treasureRooms3, instructions2)); // Expected: ["tulip"]
    }
}
