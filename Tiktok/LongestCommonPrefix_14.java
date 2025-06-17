package Jack2025.Tiktok;

public class LongestCommonPrefix_14 {
    public String longestCommonPrefix(String[] strs)
    {
        String prefix = "";

        if(strs == null || strs.length == 0)
        {
            return prefix;
        }

        for(int i = 0; i < strs[0].length(); i++)
        {
            char c = strs[0].charAt(i);
            for(int j = 1; j < strs.length; j++)
            {
                if(i >= strs[j].length() || strs[j].charAt(i) != c)
                {
                    return prefix;
                }
            }
            prefix += c;
        }

        return prefix;
    }
}
