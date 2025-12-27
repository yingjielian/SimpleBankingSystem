package Jack2025.ReStart.Intervals;

import java.util.*;
public class InsertInterval_57 {

    public int[][] inserIntervals(int[][] intervals, int[] newInterval)
    {
        List<int[]> result = new ArrayList<>();

        boolean added = false;

        for(int i = 0; i < intervals.length; i++)
        {
            int[] currInter = intervals[i];

            // Check if there's overlap
            int maxStart = Math.max(currInter[0], newInterval[0]);
            int minEnd = Math.min(currInter[1], newInterval[1]);

            if(maxStart <= minEnd)
            {
                newInterval[0] = Math.min(currInter[0], newInterval[0]);
                newInterval[1] = Math.max(currInter[1], newInterval[1]);
            }
            else {
                if(currInter[0] > newInterval[1] && !added)
                {
                    result.add(newInterval);
                    added = true;
                }
                result.add(currInter);
            }

        }

        if(added == false)
        {
            result.add(newInterval);
        }

        return result.toArray(new int[result.size()][2]);
    }
}
