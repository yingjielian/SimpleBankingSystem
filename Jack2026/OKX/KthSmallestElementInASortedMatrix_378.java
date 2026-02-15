package Jack2026.OKX;
import java.util.*;
public class KthSmallestElementInASortedMatrix_378 {

    public static int kthSmallest(int[][] matrix, int k)
    {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] - b[0]));

        for(int i = 0; i < matrix.length; i++)
        {
            pq.add(new int[]{matrix[i][0], i, 0});
        }

        while(!pq.isEmpty() && k-- > 0)
        {
            int[] curr = pq.poll();
            if(k == 0) return curr[0];

            if(curr[2] == matrix[0].length - 1) continue;
            pq.offer(new int[]{matrix[curr[1]][curr[2] + 1], curr[1], curr[2] + 1});
        }
        return -1;
    }


    public static void main(String[] strings)
    {
        System.out.println(kthSmallest(new int[][]{{1,5,9}, {10,11,13}, {12,13,15}}, 8));
    }
}
