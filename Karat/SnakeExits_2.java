package Jack2025.Karat;
import java.util.*;
public class SnakeExits_2 {
    public static void main(String[] args) {
        // Sample test cases
        char[][] board1 = {
                {'+', '+', '+', '0', '+', '0', '0'},
                {'0', '0', '+', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '+', '0', '0'},
                {'+', '+', '+', '0', '0', '+', '0'},
                {'0', '0', '0', '0', '0', '0', '0'}
        };

        char[][] board2 = {
                {'+', '+', '+', '0', '+', '0', '0'},
                {'0', '0', '0', '0', '0', '+', '0'},
                {'0', '0', '+', '0', '0', '0', '0'},
                {'0', '0', '0', '0', '+', '0', '0'},
                {'+', '+', '+', '0', '0', '0', '+'}
        };

        char[][] board3 = {
                {'+', '+', '+', '0', '+', '0', '0'},
                {'0', '0', '0', '0', '0', '0', '0'},
                {'0', '0', '+', '+', '0', '+', '0'},
                {'0', '0', '0', '0', '+', '0', '0'},
                {'+', '+', '+', '0', '0', '0', '+'}
        };

        char[][] board4 = {{'+'}};
        char[][] board5 = {{'0'}};

        System.out.println(findPassableLanes(board1)); // Rows: [4], Columns: [3, 6]
        System.out.println(findPassableLanes(board2)); // Rows: [], Columns: [3]
        System.out.println(findPassableLanes(board3)); // Rows: [1], Columns: []
        System.out.println(findPassableLanes(board4)); // Rows: [], Columns: []
        System.out.println(findPassableLanes(board5)); // Rows: [0], Columns: [0]
    }

    public static String findPassableLanes(char[][] board) {
        List<Integer> passableRows = new ArrayList<>();
        List<Integer> passableColumns = new ArrayList<>();

        // Check passable rows
        for (int i = 0; i < board.length; i++) {
            boolean isPassable = true;
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '+') {
                    isPassable = false;
                    break;
                }
            }
            if (isPassable) {
                passableRows.add(i);
            }
        }

        // Check passable columns
        if (board.length > 0) {
            for (int j = 0; j < board[0].length; j++) {
                boolean isPassable = true;
                for (int i = 0; i < board.length; i++) {
                    if (board[i][j] == '+') {
                        isPassable = false;
                        break;
                    }
                }
                if (isPassable) {
                    passableColumns.add(j);
                }
            }
        }

        return "Rows: " + passableRows + ", Columns: " + passableColumns;
    }
}
