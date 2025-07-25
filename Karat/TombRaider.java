package Jack2025.Karat;
import java.util.*;
public class TombRaider {

    public static int distance(String room) {
        Map<Character, List<Integer>> holes = new HashMap<>();
        Map<Character, List<Integer>> spheres = new HashMap<>();

        for (int i = 0; i < room.length(); i++) {
            char c = room.charAt(i);
            if (Character.isLowerCase(c)) {
                holes.computeIfAbsent(c, k -> new ArrayList<>()).add(i);
            } else if (Character.isUpperCase(c)) {
                char lowerC = Character.toLowerCase(c);
                spheres.computeIfAbsent(lowerC, k -> new ArrayList<>()).add(i);
            }
        }

        if (spheres.isEmpty()) {
            return 0;
        }

        List<Character> colors = new ArrayList<>(spheres.keySet());
        int[] minTotal = { Integer.MAX_VALUE };
        backtrack(colors, 0, spheres, holes, new ArrayList<>(), minTotal);
        return minTotal[0];
    }

    private static void backtrack(List<Character> colors, int index,
                                  Map<Character, List<Integer>> spheres,
                                  Map<Character, List<Integer>> holes,
                                  List<Integer> currentAssignments,
                                  int[] minTotal) {
        if (index == colors.size()) {
            int total = 0;
            for (int i = 0; i < currentAssignments.size(); i++) {
                char color = colors.get(i);
                int spherePos = spheres.get(color).get(0);
                int holePos = currentAssignments.get(i);
                total += Math.abs(spherePos - holePos);
            }
            if (total < minTotal[0]) {
                minTotal[0] = total;
            }
            return;
        }

        char currentColor = colors.get(index);
        List<Integer> availableHoles = holes.getOrDefault(currentColor, new ArrayList<>());
        for (int holePos : availableHoles) {
            currentAssignments.add(holePos);
            backtrack(colors, index + 1, spheres, holes, currentAssignments, minTotal);
            currentAssignments.remove(currentAssignments.size() - 1);
        }
    }

    public static void main(String[] args) {
        String room2 = "RBGYygbr";
        System.out.println(distance(room2)); // 16

        String room3 = "..........";
        System.out.println(distance(room3)); // 0

        String room4 = "abcbabbcbaAabcabcabbBabbabcabcbbC";
        System.out.println(distance(room4)); // 5

        String room5 = "rRBGYygbr";
        System.out.println(distance(room5)); // 10
    }
}
