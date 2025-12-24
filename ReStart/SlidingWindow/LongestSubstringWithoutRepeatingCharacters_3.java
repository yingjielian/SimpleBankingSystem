package Jack2025.ReStart.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithoutRepeatingCharacters_3 {

    public static Integer findLongest(String s)
    {
        char[] chars = s.toCharArray();

        int result = 0;
        int start = 0;
        Map<Character, Integer> map = new HashMap<>();

        for(int end = 0; end < chars.length; end++)
        {
            map.put(chars[end], map.getOrDefault(chars[end], 0) + 1);
            while(map.get(chars[end]) > 1)
            {
                map.put(chars[start], map.get(chars[start]) - 1);
                if(map.get(chars[start]) == 0)
                {
                    map.remove(chars[start]);
                }
                start++;
            }
            result = Math.max(result, end - start + 1);
        }

        return result;
    }

    public static void main(String[] args)
    {
        System.out.println(findLongest("substring"));
    }
}
