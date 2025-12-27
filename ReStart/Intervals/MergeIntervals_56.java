package Jack2025.ReStart.Intervals;

import java.util.*;

public class MergeIntervals_56 {
    public int[][] mergeIntervals(int[][] intervals)
    {
        if(intervals.length == 0 || intervals.length == 1) return intervals;

        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        List<int[]> result = new ArrayList<>();

        int[] currInter = intervals[0];

        for(int i = 1; i < intervals.length; i++)
        {
            if(intervals[i][0] > currInter[1])
            {
                result.add(currInter);
                currInter = intervals[i];
            }
            else {
                currInter[1] = Math.max(currInter[1], intervals[i][1]);
            }
        }
        result.add(currInter);

        return result.toArray(new int[result.size()][2]);
    }
}
