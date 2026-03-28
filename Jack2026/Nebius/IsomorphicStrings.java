package Jack2026.Nebius;

import java.util.HashMap;

public class IsomorphicStrings {
    public boolean isIsmorphic(String s, String t)
    {
        if(s.length() != t.length())
        {
            return false;
        }

        HashMap<Character, Character> map = new HashMap<>();

        for(int i = 0; i < s.length(); i++)
        {
            char a = s.charAt(i);
            char b = t.charAt(i);

            if(map.containsKey(a))
            {
                if(map.get(a) == b)
                {
                    continue;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                if(!map.containsValue(b))
                {
                    map.put(a, b);
                }
                else
                {
                    return false;
                }
            }
        }
        return true;
    }
}
