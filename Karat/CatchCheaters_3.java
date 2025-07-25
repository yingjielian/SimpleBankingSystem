package Jack2025.Karat;
import java.util.*;
public class CatchCheaters_3 {
    public static List<List<int[]>> findWordLocations(char[][] grid, List<String> words) {
        List<List<int[]>> result = new ArrayList<>();
        char[][] gridCopy = deepCopy(grid);

        for (String word : words) {
            boolean found = false;
            for (int i = 0; i < gridCopy.length && !found; i++) {
                for (int j = 0; j < gridCopy[i].length && !found; j++) {
                    if (gridCopy[i][j] == word.charAt(0)) {
                        List<int[]> path = new ArrayList<>();
                        if (dfs(gridCopy, word, i, j, 0, path)) {
                            result.add(path);
                            found = true;
                        }
                    }
                }
            }
            if (!found) {
                result.add(new ArrayList<>()); // or handle as needed
            }
        }
        return result;
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

    private static char[][] deepCopy(char[][] grid) {
        char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = new char[grid[i].length];
            System.arraycopy(grid[i], 0, copy[i], 0, grid[i].length);
        }
        return copy;
    }

    public static void main(String[] args) {
        char[][] grid1 = {
                {'b', 'a', 'b'},
                {'y', 't', 'a'},
                {'x', 'x', 't'}
        };
        List<String> words1_1 = List.of("by", "bat");
        List<List<int[]>> result1 = findWordLocations(grid1, words1_1);
        printResult(result1);

        char[][] grid2 = {
                {'A', 'B', 'A', 'B'},
                {'B', 'A', 'B', 'A'},
                {'A', 'B', 'Y', 'B'},
                {'B', 'Y', 'A', 'A'},
                {'A', 'B', 'B', 'A'}
        };
        List<String> words2_1 = List.of("ABABY", "ABY", "AAA", "ABAB", "BABB");
        List<List<int[]>> result2 = findWordLocations(grid2, words2_1);
        printResult(result2);

        List<String> words2_2 = List.of("ABABA", "ABA", "BAB", "BABA", "ABYB");
        List<List<int[]>> result3 = findWordLocations(grid2, words2_2);
        printResult(result3);
    }

    private static void printResult(List<List<int[]>> result) {
        for (List<int[]> path : result) {
            System.out.print("[");
            for (int[] coord : path) {
                System.out.print("(" + coord[0] + ", " + coord[1] + ") ");
            }
            System.out.print("] ");
        }
        System.out.println();
    }
}
