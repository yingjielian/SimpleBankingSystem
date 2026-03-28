package Jack2026.Nebius;

import java.util.HashMap;

public class WordPattern {
    public boolean wordPattern(String pattern, String s)
    {
        String[] stringArray = s.split("\\s+");
        if(pattern.length() != stringArray.length) return false;

        HashMap<Character, String> map = new HashMap<>();

        for(int i = 0; i < pattern.length(); i++)
        {
            char a = pattern.charAt(i);
            String b = stringArray[i];

            if(map.containsKey(a))
            {
                if(!map.get(a).equals(b))
                {
                    return false;
                }
            }
            else
            {
                if(map.containsValue(b))
                {
                    return false;
                }
            }
            map.put(a, b);
        }
        return true;
    }
}
