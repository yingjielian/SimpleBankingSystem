package Jack2025.Karat;
import java.util.*;
public class CatchCheaters_2 {
    public static List<int[]> findWordLocation(char[][] grid, String word) {
        List<int[]> path = new ArrayList<>();
        if (word == null || word.isEmpty()) {
            return path;
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == word.charAt(0)) {
                    if (dfs(grid, word, i, j, 0, path)) {
                        return path;
                    }
                }
            }
        }
        return path;
    }

    private static boolean dfs(char[][] grid, String word, int i, int j, int index, List<int[]> path) {
        if (index == word.length()) {
            return true;
        }
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] != word.charAt(index)) {
            return false;
        }
        path.add(new int[]{i, j});
        char temp = grid[i][j];
        grid[i][j] = '#'; // Mark as visited
        boolean found = dfs(grid, word, i, j + 1, index + 1, path) || dfs(grid, word, i + 1, j, index + 1, path);
        if (!found) {
            path.remove(path.size() - 1);
            grid[i][j] = temp;
        }
        return found;
    }

    public static void main(String[] args) {
        char[][] grid1 = {
                {'c', 'c', 'x', 't', 'i', 'b'},
                {'c', 'c', 'a', 't', 'n', 'i'},
                {'a', 'c', 'n', 'n', 't', 't'},
                {'t', 'c', 's', 'i', 'p', 't'},
                {'a', 'o', 'o', 'o', 'a', 'a'},
                {'o', 'a', 'a', 'a', 'o', 'o'},
                {'k', 'a', 'i', 'c', 'k', 'i'}
        };
        String word1 = "catnip";
        String word2 = "cccc";
        String word3 = "s";
        String word4 = "bit";
        String word5 = "aoi";
        String word6 = "ki";
        String word7 = "aaa";
        String word8 = "ooo";
        char[][] grid2 = {{'a'}};
        String word9 = "a";

        System.out.println("Test Case 1:");
        List<int[]> result1 = findWordLocation(grid1, word1);
        printResult(result1);

        System.out.println("Test Case 2:");
        List<int[]> result2 = findWordLocation(grid1, word2);
        printResult(result2);

        System.out.println("Test Case 3:");
        List<int[]> result3 = findWordLocation(grid1, word3);
        printResult(result3);

        System.out.println("Test Case 4:");
        List<int[]> result4 = findWordLocation(grid1, word4);
        printResult(result4);

        System.out.println("Test Case 5:");
        List<int[]> result5 = findWordLocation(grid1, word5);
        printResult(result5);

        System.out.println("Test Case 6:");
        List<int[]> result6 = findWordLocation(grid1, word6);
        printResult(result6);

        System.out.println("Test Case 7:");
        List<int[]> result7 = findWordLocation(grid1, word7);
        printResult(result7);

        System.out.println("Test Case 8:");
        List<int[]> result8 = findWordLocation(grid1, word8);
        printResult(result8);

        System.out.println("Test Case 9:");
        List<int[]> result9 = findWordLocation(grid2, word9);
        printResult(result9);
    }

    private static void printResult(List<int[]> result) {
        for (int[] coord : result) {
            System.out.print("(" + coord[0] + ", " + coord[1] + ") ");
        }
        System.out.println();
    }
}
