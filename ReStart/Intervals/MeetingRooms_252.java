package Jack2025.ReStart.Intervals;

import java.util.Arrays;

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
}
