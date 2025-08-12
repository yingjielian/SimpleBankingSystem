package Jack2025.SOFI;

import java.util.*;


/* The algorithm uses a sliding window with two pointers to find the smallest substring of s that contains all
characters from t, counting duplicates.
    It keeps track of how many of each character are still needed with an array,
    expanding the right pointer to include required characters and shrinking the left pointer to maintain the minimum
    size while still meeting the requirement.
 The smallest valid window found during the process is returned, or an  empty string if no valid window exists.*/
public class MinWindowSubstring_76 {
    public String minWindow(String s, String t) {

        String result = "";

        int[] tArray = new int[128];
        for (char c : t.toCharArray()) {
            tArray[c]++;
        }

        // Start index
        int from = 0;

        // Check if find all chars in t
        int total = t.length();

        // Min length
        int min = Integer.MAX_VALUE;
        int start = 0;

        for (int i = 0; i < s.length(); i++) {
            // if find char in t, total and tArray both --
            char c = s.charAt(i);
            if (tArray[c] > 0) {
                total--;
            }
            tArray[c]--;
            // Let's find one valid substring first
            // When we find it, we increase the start index
            // Then we find the min substring

            while (total == 0) {
                // Check the current substring length with our min
                int currentLength = i - start;
                if (currentLength < min) {
                    min = currentLength + 1;
                    from = start;
                }

                char startChar = s.charAt(start);
                start++;

                if (tArray[startChar] == 0) {
                    total++;
                }
                tArray[startChar]++;
            }
        }

        if (min != Integer.MAX_VALUE) {
            result = s.substring(from, from + min);
        }
        return result;
    }
}
