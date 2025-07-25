package Jack2025.Karat;
import java.util.*;
public class SnakeExits_3 {
    public static List<Integer> getNestEntranceCount(char[][] board) {
        List<Integer> entranceCounts = new ArrayList<>();
        if (board == null || board.length == 0 || board[0].length == 0) {
            return entranceCounts;
        }

        int rows = board.length;
        int cols = board[0].length;
        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == '0' && !visited[i][j]) {
                    int entranceCount = bfs(board, visited, i, j, rows, cols);
                    if (entranceCount > 0) {
                        entranceCounts.add(entranceCount);
                    }
                }
            }
        }

        Collections.sort(entranceCounts);
        return entranceCounts;
    }

    private static int bfs(char[][] board, boolean[][] visited, int startRow, int startCol, int rows, int cols) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;
        int entranceCount = 0;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];

            // Check if current cell is on the edge
            if (row == 0 || row == rows - 1 || col == 0 || col == cols - 1) {
                entranceCount++;
            }

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                        && board[newRow][newCol] == '0' && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    queue.add(new int[]{newRow, newCol});
                }
            }
        }

        return entranceCount;
    }

    public static void main(String[] args) {
        // Test cases
        char[][] board1 = {
                {'+', '+', '+', '+', '+', '+', '+', '0', '0'},
                {'+', '+', '0', '0', '0', '0', '0', '+', '+'},
                {'0', '0', '0', '0', '0', '+', '+', '0', '+'},
                {'+', '+', '0', '+', '+', '+', '+', '0', '0'},
                {'+', '+', '0', '0', '0', '0', '0', '0', '+'},
                {'+', '+', '0', '+', '+', '0', '+', '0', '+'}
        };
        System.out.println(getNestEntranceCount(board1)); // Expected: [2, 5]

        char[][] board2 = {
                {'+', '+', '+', '+', '+', '+'},
                {'0', '0', '0', '+', '0', '+'},
                {'+', '0', '+', '0', '0', '0'},
                {'+', '+', '+', '+', '+', '+'}
        };
        System.out.println(getNestEntranceCount(board2)); // Expected: [1, 1]

        char[][] board3 = {
                {'+', '0', '+', '+', '+', '0', '+', '0', '0'},
                {'+', '0', '+', '0', '0', '0', '0', '+', '+'},
                {'0', '0', '0', '0', '0', '+', '+', '0', '+'},
                {'+', '+', '+', '+', '+', '+', '+', '0', '0'},
                {'+', '+', '0', '0', '0', '0', '0', '0', '+'},
                {'+', '+', '0', '+', '+', '0', '+', '0', '+'}
        };
        System.out.println(getNestEntranceCount(board3)); // Expected: [2, 4, 3]

        char[][] board4 = {
                {'+', '+', '+'},
                {'+', '0', '+'},
                {'+', '+', '+'}
        };
        System.out.println(getNestEntranceCount(board4)); // Expected: [0]

        char[][] board5 = {
                {'+'}
        };
        System.out.println(getNestEntranceCount(board5)); // Expected: []

        char[][] board6 = {
                {'0', '0'},
                {'0', '0'}
        };
        System.out.println(getNestEntranceCount(board6)); // Expected: [4]
    }
}
