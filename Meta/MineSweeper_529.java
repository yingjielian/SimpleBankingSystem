package Jack2025.Meta;

import java.util.LinkedList;
import java.util.Queue;

public class MineSweeper_529 {
    int[][] dirs = {{-1, 1}, {0, 1}, {1, 1}, {-1, 0}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};

//    public char[][] updateBoard(char[][] board, int[] click)
//    {
//        int x = click[0];
//        int y = click[1];
//        char curr = board[x][y];
//
//        if(curr == 'M')
//        {
//            board[x][y] = 'X';
//            return board;
//        }
//
//        int count = findMines(board, x, y);
//        if(count == 0)
//        {
//            reveal(board, x, y);
//        }
//        else {
//            board[x][y] = (char)(count + '0');
//        }
//        return board;
//    }
//
//    public int findMines(char[][] board, int i, int j)
//    {
//        int count = 0;
//
//        for(int[] dir : dirs)
//        {
//            int x = i + dir[0];
//            int y = j + dir[1];
//            if(x >= 0 && x < board.length && y >= 0 && y < board[0].length)
//            {
//                if(board[x][y] == 'M' || board[x][y] == 'X')
//                    count++;
//            }
//        }
//        return count;
//    }
//
//    public void reveal(char[][] board, int i, int j)
//    {
//        if(i < 0 || i >= board.length || j < 0 || j >= board[0].length)
//            return;
//
//        if(board[i][j] == 'M' || board[i][j] == 'B')
//            return;
//
//        int count = findMines(board, i, j);
//
//        if (count != 0)
//        {
//            board[i][j] = (char)(count + '0');
//            return;
//        }
//
//        board[i][j] = 'B';
//
//        for(int[] dir : dirs)
//        {
//            reveal(board, i + dir[0], j + dir[1]);
//        }
//    }

    public char[][] updateBoard(char[][] board, int[] click)
    {
        int x = click[0];
        int y = click[1];

        if(board[x][y] == 'M')
        {
            board[x][y] = 'X';
            return board;
        }

        int count = findMines(board, x, y);

        if(count != 0)
        {
            board[x][y] = (char)(count + '0');
            return board;
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(click);

        while(!queue.isEmpty())
        {
            int[] curr = queue.poll();
            board[curr[0]][curr[1]] = 'B';

            for(int[] dir : dirs)
            {
                int i = curr[0] + dir[0];
                int j = curr[1] + dir[1];

                if(i < 0 || i >= board.length || j < 0 || j >= board[0].length)
                    continue;

                if(board[i][j] != 'E')
                    continue;

                int currCount = findMines(board, i, j);

                if(currCount != 0)
                {
                    board[i][j] = (char)(currCount + '0');
                    continue;
                }
                board[i][j] = 'B';
                queue.offer(new int[]{i, j});
            }

        }

        return board;
    }


    public int findMines(char[][] board, int i, int j)
    {
        int count = 0;
        for(int[] dir : dirs)
        {
            int x = i + dir[0];
            int y = j + dir[1];

            if(x >= 0 && x < board.length && y >= 0 && y < board[0].length )
            {
                if(board[x][y] == 'M' || board[x][y] == 'X')
                {
                    count++;
                }
            }
        }
        return count;
    }

}
