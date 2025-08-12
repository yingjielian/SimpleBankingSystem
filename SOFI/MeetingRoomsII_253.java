package Jack2025.SOFI;
import java.util.*;
public class MeetingRoomsII_253 {
    public class Interval {
        public int start, end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /*This code first sorts all meetings by their start times to process them in chronological order. It uses a
    priority  queue to keep track of meeting end times, always prioritizing the earliest finishing meeting to
    efficiently  reuse rooms. For each meeting, it either reuses an existing room if the current meeting starts after
      the earliest one ends or allocates a new room, and finally returns the total number of rooms needed.*/
    public int minMeetingRooms(List<Interval> intervals) {
        if (intervals == null || intervals.size() == 0) return 0;

        // Sort intervals by start time
        intervals.sort((a, b) -> a.start - b.start);

        // PriorityQueue to track the earliest ending meeting
        PriorityQueue<Interval> queue = new PriorityQueue<>(
                intervals.size(), (a, b) -> a.end - b.end
        );

        // Add the first meeting
        queue.offer(intervals.get(0));

        int res = 1;

        for (int i = 1; i < intervals.size(); i++) {
            Interval curr = intervals.get(i);
            Interval earliest = queue.poll();

            if (curr.start >= earliest.end) {
                // If current meeting starts after earliest meeting ends, reuse room
                earliest.end = curr.end;
            } else {
                // Need a new room
                res++;
                queue.offer(curr);
            }

            queue.offer(earliest);
        }

        return res;
    }

//    public int minMeetingRooms(int[][] intervals) {
//        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
//
//        PriorityQueue<int[]> queue = new PriorityQueue<>(
//                intervals.length, (a, b) -> (a[1] - b[1])
//        );
//
//        queue.offer(intervals[0]);
//
//        int res = 1;
//
//        for(int i = 1; i < intervals.length; i++) {
//            int[] curr = intervals[i];
//            int[] prevMeeting = queue.poll();
//            if(curr[0] >= prevMeeting[1]) {
//                prevMeeting[1] = curr[1];
//            } else {
//                res++;
//                queue.offer(curr);
//            }
//            queue.offer(prevMeeting);
//        }
//
//        return res;
//    }
}
