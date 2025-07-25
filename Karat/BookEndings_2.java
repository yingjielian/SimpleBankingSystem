package Jack2025.Karat;

import java.util.*;

public class BookEndings_2 {
    public static List<Integer> findGoodEndings(int[] goodEndings, int[] badEndings, int[][] choices) {
        Set<Integer> goodSet = new HashSet<>();
        for (int end : goodEndings) {
            goodSet.add(end);
        }
        Set<Integer> badSet = new HashSet<>();
        for (int end : badEndings) {
            badSet.add(end);
        }

        Set<Integer> reachableGoodEndings = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(1);
        visited.add(1);

        while (!queue.isEmpty()) {
            int currentPage = queue.poll();

            if (goodSet.contains(currentPage)) {
                reachableGoodEndings.add(currentPage);
                continue;
            }
            if (badSet.contains(currentPage)) {
                continue;
            }

            int[] choice = findChoice(choices, currentPage);
            if (choice != null) {
                int nextPage1 = choice[1];
                int nextPage2 = choice[2];
                if (!visited.contains(nextPage1)) {
                    visited.add(nextPage1);
                    queue.add(nextPage1);
                }
                if (!visited.contains(nextPage2)) {
                    visited.add(nextPage2);
                    queue.add(nextPage2);
                }
            } else {
                int nextPage = currentPage + 1;
                if (!visited.contains(nextPage)) {
                    visited.add(nextPage);
                    queue.add(nextPage);
                }
            }
        }

        List<Integer> result = new ArrayList<>(reachableGoodEndings);
        Collections.sort(result);
        return result;
    }

    private static int[] findChoice(int[][] choices, int page) {
        for (int[] choice : choices) {
            if (choice[0] == page) {
                return choice;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] goodEndings = {10, 15, 25, 34};
        int[] badEndings = {21, 30, 40};

        int[][] choices1 = {{3, 16, 24}};
        System.out.println(findGoodEndings(goodEndings, badEndings, choices1)); // [25]

        int[][] choices2 = {{3, 16, 20}};
        System.out.println(findGoodEndings(goodEndings, badEndings, choices2)); // []

        int[][] choices3 = {{3, 2, 19}, {20, 21, 34}};
        System.out.println(findGoodEndings(goodEndings, badEndings, choices3)); // [34]

        int[][] choices4 = {};
        System.out.println(findGoodEndings(goodEndings, badEndings, choices4)); // [10]

        int[][] choices5 = {{9, 16, 26}, {14, 16, 13}, {27, 29, 28}, {28, 15, 34}, {29, 30, 38}};
        System.out.println(findGoodEndings(goodEndings, badEndings, choices5)); // [15, 34]

        int[][] choices6 = {{9, 16, 26}, {13, 31, 14}, {14, 16, 13}, {27, 12, 24}, {32, 34, 15}};
        System.out.println(findGoodEndings(goodEndings, badEndings, choices6)); // [15, 25, 34]

        int[][] choices7 = {{3, 9, 10}};
        System.out.println(findGoodEndings(goodEndings, badEndings, choices7)); // [10]
    }
}
