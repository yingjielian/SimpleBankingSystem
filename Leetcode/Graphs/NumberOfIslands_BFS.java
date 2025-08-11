package Jack2025.Leetcode.Graphs;

import java.util.LinkedList;
import java.util.Queue;

public class NumberOfIslands_BFS {

    public int numIslands(char[][] grid) {
        int count = 0;
        int row = grid.length;
        int col = grid[0].length;
        int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        boolean[][] visited = new boolean[row][col];

        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                if(!visited[i][j] && grid[i][j] == '1') {
                    bfs(grid, i, j, visited, directions);
                    count++;
                }
            }
        }

        return count;
    }

    public void bfs(char[][] grid, int i, int j, boolean[][] visited, int[][] directions)
    {
        int m = grid.length;
        int n = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});
        visited[i][j] = true;

        // bfs
        while (!queue.isEmpty())
        {
            int[] curr = queue.poll();
            for(int[] dir : directions)
            {
                int x = curr[0] + dir[0];
                int y = curr[1] + dir[1];
                if(x >= 0 && x < m && y >= 0 && y < n)
                {
                    if(!visited[x][y] && grid[x][y] == '1')
                    {
                        queue.offer(new int[]{x, y});
                        visited[x][y] = true;
                    }
                }
            }
        }
    }


}
