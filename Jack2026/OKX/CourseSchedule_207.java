package Jack2026.OKX;

import java.util.LinkedList;
import java.util.Queue;

public class CourseSchedule_207 {
    public boolean canFinish(int numCourses, int[][] prerequisites)
    {
        int[] indegrees = new int[numCourses];

        for(int[] pair : prerequisites)
        {
            indegrees[pair[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        for(int i = 0; i < numCourses; i++)
        {
            if(indegrees[i] == 0)
            {
                queue.offer(i);
            }
        }

        while(!queue.isEmpty())
        {
            int course = queue.poll();

            for(int[] pair : prerequisites)
            {
                if(indegrees[pair[0]] == 0)
                {
                    continue;
                }

                if(pair[1] == course)
                {
                    indegrees[pair[0]]--;
                }

                if(indegrees[pair[0]] == 0)
                {
                    queue.offer(pair[0]);
                }
            }
        }

        for(int i = 0; i < numCourses; i++)
        {
            if(indegrees[i] != 0)
            {
                return false;
            }
        }
        return true;
    }
}
