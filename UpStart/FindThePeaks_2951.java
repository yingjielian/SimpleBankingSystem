package Jack2025.UpStart;
import java.util.*;

public class FindThePeaks_2951 {
    public static int findPeaks(List<Double> nums) {
        if (nums == null || nums.size() < 3) return 0;

        int highPeaks = 0;
        int lowPeaks = 0;

        for (int i = 1; i < nums.size() - 1; i++) {
            double prev = nums.get(i - 1);
            double curr = nums.get(i);
            double next = nums.get(i + 1);

            // High peak
            if (curr > prev && curr > next) {
                highPeaks++;
            }

            // Low peak
            if (curr < prev && curr < next) {
                lowPeaks++;
            }
        }

        // Return total number of peaks
        return highPeaks + lowPeaks;
    }

    public static void main(String[] args) {

        // Test Case 1: Mixed real numbers, both high & low peaks
        List<Double> nums1 = Arrays.asList(5.8, 13.7, 9.4, 3.2, 7.1, 2.6);
        System.out.println("Test 1 result: " + findPeaks(nums1)); // Expected: 3
    }
}
