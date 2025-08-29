package Jack2025.Uber;

public class StraightLineWordSearch {

    // 八个方向：右，下，右下，右上，左，下，左下，左上
    private static final int[][] DIRECTIONS = {
            {0, 1}, {1, 0}, {1, 1}, {-1, 1},
            {0, -1}, {-1, 0}, {-1, -1}, {1, -1}
    };

    public static boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == word.charAt(0)) {
                    // 尝试沿八个方向匹配整个单词
                    for (int[] dir : DIRECTIONS) {
                        if (checkDirection(board, word, r, c, dir[0], dir[1])) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkDirection(char[][] board, String word, int row, int col, int dr, int dc) {
        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < word.length(); i++) {
            int r = row + i * dr;
            int c = col + i * dc;

            if (r < 0 || r >= rows || c < 0 || c >= cols || board[r][c] != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        char[][] board = {
                {'A','R','C','E','U'},
                {'S','F','E','S','E'},
                {'A','D','E','B','E'},
                {'A','D','E','E','U'}
        };

        String word1 = "AFEB";   // true (diagonal)
        String word2 = "ESE";   // true (vertical)
        String word3 = "SEE";   // false
        String word4 = "BFEE";  // false
        String word5 = "UBER";  // false

        System.out.println(exist(board, word1));
        System.out.println(exist(board, word2));
        System.out.println(exist(board, word3));
        System.out.println(exist(board, word4));
        System.out.println(exist(board, word5));
    }
}

