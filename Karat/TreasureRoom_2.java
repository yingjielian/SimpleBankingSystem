package Jack2025.Karat;
import java.util.*;
public class TreasureRoom_2 {

    public static int minInstructions(List<Integer> instructions, int money) {
        int n = instructions.size();
        if (n == 0) return -1;
        int target = n - 1; // The treasure is in the last room (index n-1)

        // Each state is represented by (currentRoom, remainingMoney)
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, money});

        // Visited tracks the minimum steps to reach (room, money)
        Map<String, Integer> visited = new HashMap<>();
        visited.put(0 + "," + money, 0);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int room = current[0];
            int remainingMoney = current[1];
            int steps = visited.get(room + "," + remainingMoney);

            if (room == target) {
                return steps;
            }

            int instruction = instructions.get(room);
            if (instruction == 9 && room == target - 1) {
                // Reached the last instruction which is 9, no move needed
                return steps + 1;
            }

            // Generate possible next moves
            List<Integer> possibleMoves = new ArrayList<>();
            possibleMoves.add(instruction); // original instruction
            if (remainingMoney > 0) {
                if (instruction > 1) {
                    possibleMoves.add(instruction - 1); // pay to decrease by 1
                }
                possibleMoves.add(instruction + 1); // pay to increase by 1
            }

            for (int move : possibleMoves) {
                int nextRoom = room + move;
                int cost = (move == instruction) ? 0 : 1;
                int newRemainingMoney = remainingMoney - cost;

                if (nextRoom <= target && newRemainingMoney >= 0) {
                    String key = nextRoom + "," + newRemainingMoney;
                    if (!visited.containsKey(key)) {
                        visited.put(key, steps + 1);
                        queue.add(new int[]{nextRoom, newRemainingMoney});
                    }
                }
            }
        }

        return -1; // No path found
    }

    public static void main(String[] args) {
        // Example 1
        List<Integer> instructions1 = Arrays.asList(1, 1, 1, 9);
        System.out.println(minInstructions(instructions1, 0)); // Output: 3

        // Example 2
        List<Integer> instructions2 = Arrays.asList(2, 1, 1, 9);
        System.out.println(minInstructions(instructions2, 1)); // Output: 3

        // Example 3
        List<Integer> instructions3 = Arrays.asList(2, 2, 2, 9);
        System.out.println(minInstructions(instructions3, 2)); // Output: 2
    }
}
