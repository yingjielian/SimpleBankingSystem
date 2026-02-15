package Jack2026.Pinterest;
import java.util.*;
public class FindRestaurantIntervals {
    public List<List<String>> getAvailableIntervals(
            String open, String close, int capacity, List<List<String>> reservations) {

        int openMin = toMinutes(open);
        int closeMin = toMinutes(close);
        List<List<String>> res = new ArrayList<>();
        if (openMin >= closeMin) return res;

        // Minute granularity: 0..1440
        int[] diff = new int[1441 + 1]; // a bit extra safety

        // Build occupancy diff array within [openMin, closeMin)
        for (List<String> r : reservations) {
            int s = toMinutes(r.get(0));
            int e = toMinutes(r.get(1));
            int seats = Integer.parseInt(r.get(2));

            // Clamp to operating hours
            int start = Math.max(s, openMin);
            int end = Math.min(e, closeMin);

            // No overlap with operating window
            if (start >= end) continue;

            diff[start] += seats;
            diff[end] -= seats;
        }

        int occupied = 0;
        int intervalStart = openMin;
        int prevAvail = -1;

        for (int t = openMin; t < closeMin; t++) {
            occupied += diff[t]; // applies to interval [t, t+1)
            int avail = Math.max(0, capacity - occupied);

            if (t == openMin) {
                prevAvail = avail;
                intervalStart = openMin;
            } else if (avail != prevAvail) {
                // close previous interval at boundary t
                addInterval(res, intervalStart, t, prevAvail);
                intervalStart = t;
                prevAvail = avail;
            }
        }

        // Close last interval
        addInterval(res, intervalStart, closeMin, prevAvail);
        return res;
    }

    // Helpers
    private void addInterval(List<List<String>> out, int start, int end, int seats) {
        if (start >= end) return; // skip zero-length
        out.add(Arrays.asList(toHHMM(start), toHHMM(end), String.valueOf(seats)));
    }

    private int toMinutes(String hhmm) {
        int h = (hhmm.charAt(0) - '0') * 10 + (hhmm.charAt(1) - '0');
        int m = (hhmm.charAt(3) - '0') * 10 + (hhmm.charAt(4) - '0');
        return h * 60 + m;
    }

    private String toHHMM(int minutes) {
        int h = minutes / 60;
        int m = minutes % 60;
        return String.format("%02d:%02d", h, m);
    }
}
