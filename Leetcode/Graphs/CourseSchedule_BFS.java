package Jack2025.Leetcode.Graphs;

import java.util.LinkedList;
import java.util.*;

public class CourseSchedule_BFS {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];

        for (int[] pair : prerequisites)
        {
            indegree[pair[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        for(int i = 0; i < indegree.length; i++)
        {
            if(indegree[i] == 0)
            {
                queue.offer(i);
            }
        }

        while(!queue.isEmpty())
        {
            int cur = queue.poll();

            for(int[] pair : prerequisites)
            {
                if(indegree[pair[0]] == 0)
                {
                    continue;
                }

                if(pair[1] == cur)
                {
                    indegree[pair[0]]--;
                }

                if(indegree[pair[0]] == 0)
                {
                    queue.offer(pair[0]);
                }
            }
        }

        for(int i = 0; i < indegree.length; i++)
        {
            if(indegree[i] != 0)
            {
                return false;
            }
        }

        return true;



    }
}
