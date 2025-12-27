package Jack2025.ReStart.Intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NonoverlappingIntervals_435 {

    public int eraseOverlapIntervals(int[][] intervals)
    {
        if(intervals.length == 0 || intervals.length == 1) return 0;

        // Sort by end time
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        int count = 1;
        int currInterEnd = intervals[0][1];

        for(int i = 1; i < intervals.length; i++)
        {
            if(intervals[i][0] >= currInterEnd)
            {
                currInterEnd = intervals[i][1];
                count++;
            }
        }

        return intervals.length - count;

    }

    public int[][] mergeIntervals(int[][] intervals)
    {
        if(intervals.length == 0 || intervals.length == 1) return intervals;

        List<int[]> result = new ArrayList<>();
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int[] currInter = intervals[0];

        for(int i = 1; i < intervals.length; i++)
        {
            if(currInter[1] < intervals[i][0])
            {
                result.add(currInter);
                currInter = intervals[i];
            }
            else {
                currInter[1] = Math.max(currInter[1], intervals[i][1]);
            }
        }

        result.add(currInter);

        return result.toArray(new int[result.size()][]);
    }

}
