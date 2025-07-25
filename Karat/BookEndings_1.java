package Jack2025.Karat;

import java.util.HashSet;
import java.util.Set;

public class BookEndings_1 {
    public static int findEnding(int[] endings, int[][] choices, int option) {
        Set<Integer> visited = new HashSet<>();
        int currentPage = 1;

        while (true) {
            if (visited.contains(currentPage)) {
                return -1; // loop detected
            }
            visited.add(currentPage);

            // Check if current page is an ending
            if (contains(endings, currentPage)) {
                return currentPage;
            }

            // Check if current page has a choice
            int[] choice = findChoice(choices, currentPage);
            if (choice != null) {
                int nextPage;
                if (option == 1) {
                    nextPage = choice[1];
                } else {
                    nextPage = choice[2];
                }
                currentPage = nextPage;
            } else {
                currentPage++; // no choice, proceed to next page
            }
        }
    }

    private static boolean contains(int[] endings, int page) {
        for (int end : endings) {
            if (end == page) {
                return true;
            }
        }
        return false;
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
        // Test cases
        int[] endings = {6, 15, 21, 30};

        int[][] choices1 = {{3, 14, 2}};
        System.out.println(findEnding(endings, choices1, 1)); // 15
        System.out.println(findEnding(endings, choices1, 2)); // -1

        int[][] choices2 = {{5, 11, 28}, {9, 19, 29}, {14, 16, 20}, {18, 7, 22}, {25, 6, 30}};
        System.out.println(findEnding(endings, choices2, 1)); // 21
        System.out.println(findEnding(endings, choices2, 2)); // 30

        int[][] choices3 = {};
        System.out.println(findEnding(endings, choices3, 1)); // 6
        System.out.println(findEnding(endings, choices3, 2)); // 6

        int[][] choices4 = {{2, 10, 15}, {3, 4, 10}, {4, 3, 15}, {10, 3, 15}};
        System.out.println(findEnding(endings, choices4, 1)); // -1
        System.out.println(findEnding(endings, choices4, 2)); // 15
    }
}
