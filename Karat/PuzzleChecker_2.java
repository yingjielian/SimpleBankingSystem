package Jack2025.Karat;
import java.util.*;
public class PuzzleChecker_2 {
    public static boolean validateNonogram(int[][] matrix, List<List<Integer>> rowInstructions, List<List<Integer>> colInstructions) {
        if (matrix == null || rowInstructions == null || colInstructions == null) {
            return false;
        }

        int rows = matrix.length;
        int cols = rows > 0 ? matrix[0].length : 0;

        // Check if instructions match matrix dimensions
        if (rowInstructions.size() != rows || colInstructions.size() != cols) {
            return false;
        }

        // Validate all rows
        for (int i = 0; i < rows; i++) {
            if (!validateSequence(matrix[i], rowInstructions.get(i))) {
                return false;
            }
        }

        // Validate all columns
        for (int j = 0; j < cols; j++) {
            int[] column = new int[rows];
            for (int i = 0; i < rows; i++) {
                column[i] = matrix[i][j];
            }
            if (!validateSequence(column, colInstructions.get(j))) {
                return false;
            }
        }

        return true;
    }

    private static boolean validateSequence(int[] sequence, List<Integer> instruction) {
        List<Integer> actualRuns = getRuns(sequence, 0);
        return actualRuns.equals(instruction);
    }

    private static List<Integer> getRuns(int[] sequence, int target) {
        List<Integer> runs = new ArrayList<>();
        int currentRun = 0;

        for (int num : sequence) {
            if (num == target) {
                currentRun++;
            } else {
                if (currentRun > 0) {
                    runs.add(currentRun);
                    currentRun = 0;
                }
            }
        }

        // Add the last run if it exists
        if (currentRun > 0) {
            runs.add(currentRun);
        }

        return runs;
    }

    public static void main(String[] args) {
        // Example 1 (should return true)
        int[][] matrix1 = {
                {1, 1, 1, 1},
                {0, 1, 1, 1},
                {0, 1, 0, 0},
                {1, 1, 0, 1},
                {0, 0, 1, 1}
        };
        List<List<Integer>> rows1_1 = List.of(
                List.of(),
                List.of(1),
                List.of(1, 2),
                List.of(1),
                List.of(2)
        );
        List<List<Integer>> columns1_1 = List.of(
                List.of(2, 1),
                List.of(1),
                List.of(2),
                List.of(1)
        );
        System.out.println("Example 1: " + validateNonogram(matrix1, rows1_1, columns1_1)); // true

        // Example 2 (should return false)
        List<List<Integer>> rows1_2 = List.of(
                List.of(),
                List.of(),
                List.of(1),
                List.of(1),
                List.of(1, 1)
        );
        List<List<Integer>> columns1_2 = List.of(
                List.of(2),
                List.of(1),
                List.of(2),
                List.of(1)
        );
        System.out.println("Example 2: " + validateNonogram(matrix1, rows1_2, columns1_2)); // false

        // Example 3 (should return false)
        int[][] matrix2 = {
                {1, 1},
                {0, 0},
                {0, 0},
                {1, 0}
        };
        List<List<Integer>> rows2_1 = List.of(
                List.of(),
                List.of(2),
                List.of(2),
                List.of(1)
        );
        List<List<Integer>> columns2_1 = List.of(
                List.of(1, 1),
                List.of(3)
        );
        System.out.println("Example 3: " + validateNonogram(matrix2, rows2_1, columns2_1)); // false
    }

}
