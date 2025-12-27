package Jack2025.ReStart.Intervals;

import java.util.*;

public class MeetingRooms_252 {

    public boolean canAttendMeetings(int[][] intervals)
    {
        if(intervals.length == 0 || intervals.length == 1) return true;

        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        for(int i = 1; i < intervals.length; i++)
        {
            int[] prevInterval = intervals[i - 1];
            int[] currInterval = intervals[i];
            if(currInterval[0] < prevInterval[1])
            {
                return false;
            }
        }


        return true;
    }

    public int maxScore(int[] cardPoints, int k)
    {

        int result = 0;
        int total = 0;
        int start = 0;
        int state = 0;
        int subLength = cardPoints.length - k;

        for(int i : cardPoints)
        {
            total += i;
        }

        if(cardPoints.length == k) return total;

        for(int end = 0; end < cardPoints.length; end++)
        {
            state += cardPoints[end];

            if(end - start + 1 == subLength)
            {
                result = Math.max(result, total - state);
                state -= cardPoints[start];
                start++;
            }
        }

        return result;
    }
}
