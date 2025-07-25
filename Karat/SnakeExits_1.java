package Jack2025.Karat;
import java.util.*;
public class SnakeExits_1 {
    public static int[] findExit(char[][] board, int[] start) {
        int rows = board.length;
        if (rows == 0) return null;
        int cols = board[0].length;

        int startRow = start[0];
        int startCol = start[1];

        // Check if the start position is invalid
        if (startRow < 0 || startRow >= rows || startCol < 0 || startCol >= cols || board[startRow][startCol] == '+') {
            return null;
        }

        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // up, down, left, right

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentRow = current[0];
            int currentCol = current[1];

            // Check if current position is an exit (edge and not the start)
            if ((currentRow == 0 || currentRow == rows - 1 || currentCol == 0 || currentCol == cols - 1) &&
                    !(currentRow == startRow && currentCol == startCol)) {
                return new int[]{currentRow, currentCol};
            }

            for (int[] dir : directions) {
                int newRow = currentRow + dir[0];
                int newCol = currentCol + dir[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols &&
                        board[newRow][newCol] != '+' && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    queue.add(new int[]{newRow, newCol});
                }
            }
        }

        return null; // no exit found
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
        int[] start1_1 = {2, 0};
        int[] start1_2 = {0, 7};
        int[] start1_3 = {5, 2};
        int[] start1_4 = {5, 5};

        System.out.println(arrayToString(findExit(board1, start1_1))); // Expected: (5, 2)
        System.out.println(arrayToString(findExit(board1, start1_2))); // Expected: (0, 8)
        System.out.println(arrayToString(findExit(board1, start1_3))); // Expected: (2, 0) or (5, 5)
        System.out.println(arrayToString(findExit(board1, start1_4))); // Expected: (5, 7)

        char[][] board2 = {
                {'+', '+', '+', '+', '+', '+', '+'},
                {'0', '0', '0', '0', '+', '0', '+'},
                {'+', '0', '+', '0', '+', '0', '0'},
                {'+', '0', '0', '0', '+', '+', '+'},
                {'+', '+', '+', '+', '+', '+', '+'}
        };
        int[] start2_1 = {1, 0};
        int[] start2_2 = {2, 6};

        System.out.println(arrayToString(findExit(board2, start2_1))); // Expected: null
        System.out.println(arrayToString(findExit(board2, start2_2))); // Expected: null

        char[][] board3 = {
                {'+', '0', '+', '0', '+'},
                {'0', '0', '+', '0', '0'},
                {'+', '0', '+', '0', '+'},
                {'0', '0', '+', '0', '0'},
                {'+', '0', '+', '0', '+'}
        };
        int[] start3_1 = {0, 1};
        int[] start3_2 = {4, 1};
        int[] start3_3 = {0, 3};
        int[] start3_4 = {4, 3};

        System.out.println(arrayToString(findExit(board3, start3_1))); // Expected: (1, 0)
        System.out.println(arrayToString(findExit(board3, start3_2))); // Expected: (3, 0)
        System.out.println(arrayToString(findExit(board3, start3_3))); // Expected: (1, 4)
        System.out.println(arrayToString(findExit(board3, start3_4))); // Expected: (3, 4)

        char[][] board4 = {
                {'+', '0', '+', '0', '+'},
                {'0', '0', '0', '0', '0'},
                {'+', '+', '+', '+', '+'},
                {'0', '0', '0', '0', '0'},
                {'+', '0', '+', '0', '+'}
        };
        int[] start4_1 = {1, 0};
        int[] start4_2 = {1, 4};
        int[] start4_3 = {3, 0};
        int[] start4_4 = {3, 4};

        System.out.println(arrayToString(findExit(board4, start4_1))); // Expected: (0, 1)
        System.out.println(arrayToString(findExit(board4, start4_2))); // Expected: (0, 3)
        System.out.println(arrayToString(findExit(board4, start4_3))); // Expected: (4, 1)
        System.out.println(arrayToString(findExit(board4, start4_4))); // Expected: (4, 3)

        char[][] board5 = {
                {'+', '0', '0', '0', '+'},
                {'+', '0', '+', '0', '+'},
                {'+', '0', '0', '0', '+'},
                {'+', '0', '+', '0', '+'}
        };
        int[] start5_1 = {0, 1};
        int[] start5_2 = {3, 1};

        System.out.println(arrayToString(findExit(board5, start5_1))); // Expected: (0, 2)
        System.out.println(arrayToString(findExit(board5, start5_2))); // Expected: (0, 1)
    }

    private static String arrayToString(int[] arr) {
        if (arr == null) return "null";
        return "(" + arr[0] + ", " + arr[1] + ")";
    }
}
