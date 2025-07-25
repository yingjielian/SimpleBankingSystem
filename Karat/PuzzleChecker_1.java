package Jack2025.Karat;
import java.util.*;
public class PuzzleChecker_1 {

    public static boolean isValidMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        int n = matrix.length;

        // Check if it's a square matrix
        for (int[] row : matrix) {
            if (row == null || row.length != n) {
                return false;
            }
        }

        // Check each row
        for (int i = 0; i < n; i++) {
            boolean[] seen = new boolean[n + 1]; // 1-based indexing
            for (int j = 0; j < n; j++) {
                int num = matrix[i][j];
                if (num < 1 || num > n || seen[num]) {
                    return false;
                }
                seen[num] = true;
            }
        }

        // Check each column
        for (int j = 0; j < n; j++) {
            boolean[] seen = new boolean[n + 1]; // 1-based indexing
            for (int i = 0; i < n; i++) {
                int num = matrix[i][j];
                if (num < 1 || num > n || seen[num]) {
                    return false;
                }
                seen[num] = true;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        // Test cases
        int[][] validMatrix = {
                {1, 2, 3},
                {2, 3, 1},
                {3, 1, 2}
        };

        int[][] invalidRowMatrix = {
                {1, 2, 3},
                {2, 3, 1},
                {3, 1, 1} // duplicate in last row
        };

        int[][] invalidColMatrix = {
                {1, 2, 3},
                {2, 3, 1},
                {3, 2, 1} // duplicate in middle column
        };

        System.out.println("Valid matrix: " + isValidMatrix(validMatrix)); // true
        System.out.println("Invalid row matrix: " + isValidMatrix(invalidRowMatrix)); // false
        System.out.println("Invalid column matrix: " + isValidMatrix(invalidColMatrix)); // false
    }
}
