package Jack2025.SOFI;
import java.util.*;

public class MeetingRooms_252 {
    public class Interval {
        public int start, end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

        /*This code first sorts all meeting intervals by their start time. It then iterates through each interval,
        checking if the current meeting starts before the previous one ends; if so, it returns false because the
        meetings overlap. If no overlaps are found, it returns true, meaning all meetings can be attended.*/
    public boolean canAttendMeetings(List<Interval> intervals) {
        Collections.sort(intervals, (a, b) -> a.start - b.start);

        int lastEnd = -1;

        for(Interval inter : intervals)
        {
            if(inter.start < lastEnd)
            {
                return false;
            }
            lastEnd = inter.end;
        }
        return true;
    }

//    public boolean canAttendMeetings(int[][] intervals) {
//        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
//
//        int lastEnd = -1;
//
//        for(int[] inter : intervals) {
//            if(inter[0] < lastEnd) {
//                return false;
//            }
//
//            lastEnd = inter[1];
//        }
//
//        return true;
//    }
}
