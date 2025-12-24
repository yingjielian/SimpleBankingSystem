package Jack2025.ReStart.SlidingWindow;

import java.util.*;

public class LongestRepeatingCharacterReplacement_424 {


    public static int characterReplacement(String s, int k)
    {
        int maxLength = 0;
        int maxFrequence = 0;
        Map<Character, Integer> map = new HashMap<>();
        int start = 0;
        char[] chars = s.toCharArray();

        for(int end = 0; end < chars.length; end++)
        {
            map.put(chars[end], map.getOrDefault(chars[end], 0) + 1);
            maxFrequence = Math.max(maxFrequence, map.get(chars[end]));

            // maxFreq + k < windowLength(end - start + 1)
            while(maxFrequence + k < end - start + 1)
            {
                map.put(chars[start], map.get(chars[start]) - 1);
                start++;
            }
            maxLength = Math.max(maxLength, end - start + 1);
        }

        return maxLength;
    }
    public static void main(String[] args)
    {
        System.out.println(characterReplacement("AABABBA", 1));
    }

}
