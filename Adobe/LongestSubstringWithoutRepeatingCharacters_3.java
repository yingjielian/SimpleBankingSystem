package Jack2025.Adobe;

import java.util.*;
public class LongestSubstringWithoutRepeatingCharacters_3 {
    public int lengthOfLongestSubstring(String s)
    {
        if(s == null || s.length() == 0) return 0;
        Map<Character, Integer> charToIndex = new HashMap<>();
        int result = Integer.MIN_VALUE;
        int left = 0;

        for(int i = 0; i < s.length(); i++)
        {
            char charIndex = s.charAt(i);
            if(charToIndex.containsKey(charIndex))
            {
                left = Math.max(left, charToIndex.get(charIndex) + 1);
            }

            result = Math.max(result, i - left + 1);
            charToIndex.put(charIndex, i);
        }

        return result;
    }
}
