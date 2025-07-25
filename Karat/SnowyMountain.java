package Jack2025.Karat;
import java.util.*;
public class SnowyMountain {
    public static int[] bestDayToCross(int[] initialAltitudes, int[][] snowForecast) {
        int bestDay = -1;
        int minClimbs = Integer.MAX_VALUE;

        int days = snowForecast.length;
        int positions = initialAltitudes.length;

        // Make a copy of initial altitudes to modify
        int[] currentAltitudes = Arrays.copyOf(initialAltitudes, positions);
        // Track last snow day for each position
        int[] lastSnowDay = new int[positions];
        Arrays.fill(lastSnowDay, -2); // Initialize to -2 (no snow yet)

        for (int day = 0; day < days; day++) {
            // Apply morning snow
            for (int pos = 0; pos < positions; pos++) {
                int snowAmount = snowForecast[day][pos];
                if (snowAmount > 0) {
                    currentAltitudes[pos] += snowAmount;
                    lastSnowDay[pos] = day;
                }
            }

            // Check if we can cross today (evening after potential melting)
            int[] eveningAltitudes = getEveningAltitudes(currentAltitudes, lastSnowDay, day);
            int climbs = calculateClimbs(eveningAltitudes);

            if (climbs != -1) {
                if (climbs < minClimbs || (climbs == minClimbs && day < bestDay)) {
                    minClimbs = climbs;
                    bestDay = day;
                }
            }

            // Prepare for next day by applying evening melting
            currentAltitudes = getEveningAltitudes(currentAltitudes, lastSnowDay, day);
        }

        return bestDay == -1 ? new int[]{-1, -1} : new int[]{bestDay, minClimbs};
    }

    private static int[] getEveningAltitudes(int[] currentAltitudes, int[] lastSnowDay, int currentDay) {
        int[] eveningAltitudes = Arrays.copyOf(currentAltitudes, currentAltitudes.length);
        for (int pos = 0; pos < eveningAltitudes.length; pos++) {
            if (currentDay - lastSnowDay[pos] >= 2) {
                // Snow starts melting
                eveningAltitudes[pos] = Math.max(0, eveningAltitudes[pos] - 1);
            }
        }
        return eveningAltitudes;
    }

    private static int calculateClimbs(int[] altitudes) {
        int climbs = 0;
        for (int i = 1; i < altitudes.length; i++) {
            int diff = Math.abs(altitudes[i] - altitudes[i-1]);
            if (diff > 1) {
                return -1; // Can't cross today
            }
            climbs += diff;
        }
        return climbs;
    }

    // Test cases
    public static void main(String[] args) {
        // Example from problem statement
        int[] altitudes1 = {0, 1, 2, 1};
        int[][] snow1 = {{1, 0, 1, 0}, {0, 0, 0, 0}, {1, 1, 0, 2}};
        System.out.println(Arrays.toString(bestDayToCross(altitudes1, snow1))); // [2, 1]

        // Other test cases mentioned in problem
        int[] altitudes2 = {0, 0, 0, 0};
        int[][] snow2 = {{0, 0, 0, 0}};
        System.out.println(Arrays.toString(bestDayToCross(altitudes2, snow2))); // [0, 0]

        int[] altitudes3 = {1, 1, 1, 1};
        int[][] snow3 = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        System.out.println(Arrays.toString(bestDayToCross(altitudes3, snow3))); // [2, 0]

        int[] altitudes4 = {0, 3, 0};
        int[][] snow4 = {{0, 0, 0}, {0, 0, 0}};
        System.out.println(Arrays.toString(bestDayToCross(altitudes4, snow4))); // [-1, -1]
    }
}
