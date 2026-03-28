package Jack2023;

import java.util.List;
import java.util.PriorityQueue;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
      class Interval {
      public int start, end;
      public Interval(int start, int end) {
          this.start = start;
          this.end = end;
      }
  }

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
    public static void main(String[] args) {

    }
}