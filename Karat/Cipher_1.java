package Jack2025.Karat;
import java.util.*;
public class Cipher_1 {
    public static String transpose(String message, int rows, int cols) {
        // Create the matrix
        char[][] matrix = new char[rows][cols];

        // Fill the matrix row-wise with the message
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (index < message.length()) {
                    matrix[i][j] = message.charAt(index++);
                } else {
                    matrix[i][j] = ' '; // pad with space if needed
                }
            }
        }

        // Read the matrix column-wise
        StringBuilder encrypted = new StringBuilder();
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                encrypted.append(matrix[i][j]);
            }
        }

        return encrypted.toString().trim(); // trim to remove trailing spaces if any
    }

    public static void main(String[] args) {
        String message1 = "One does not simply walk into Mordor";
        int rows1 = 6, cols1 = 6;
        System.out.println(transpose(message1, rows1, cols1));
        // Expected: "Oe y Mnss ioe iwnr nmatddoploootlk r"

        String message2 = "1.21 gigawatts!";
        int rows2 = 5, cols2 = 3;
        System.out.println(transpose(message2, rows2, cols2));
        // Expected: "11iwt. gas2gat!"

        int rows3 = 3, cols3 = 5;
        System.out.println(transpose(message2, rows3, cols3));
        // Expected: "1ga.it2gt1as w!"
    }
}
