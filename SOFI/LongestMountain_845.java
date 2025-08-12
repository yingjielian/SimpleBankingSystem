package Jack2025.SOFI;
import java.util.*;

/*This code uses two arrays, up and down, to track the length of increasing and decreasing sequences ending or starting
at each index.
        It first fills up by counting consecutive increases from left to right, then fills down by counting consecutive
        decreases from right to left.
        If both up[i] and down[i] are nonzero (indicating a peak), it updates the result with the total mountain length
        up[i] + down[i] + 1, and finally returns the longest found.*/
public class LongestMountain_845 {

    public int longestMountain(int[] arr) {
        int[] up = new int[arr.length];
        int[] down = new int[arr.length];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[i - 1]) {
                up[i] = up[i - 1] + 1;
            }
        }

        int res = 0;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                down[i] = down[i + 1] + 1;
            }

            if (up[i] != 0 && down[i] != 0) {
                res = Math.max(res, up[i] + down[i] + 1);
            }
        }

        return res;
    }
}
