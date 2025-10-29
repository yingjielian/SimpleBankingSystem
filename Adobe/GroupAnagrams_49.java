package Jack2025.Adobe;

import java.util.*;
public class GroupAnagrams_49 {

    public List<List<String>> groupAnagrams(String[] strs)
    {
        Map<String, List<String>> stringToAnagrams = new HashMap<>();

        for(String s : strs)
        {
            try{
                if(s == null)
                {
                    throw new IllegalArgumentException("Found null string elements.");
                }
                String sortedString = sortString(s);
                stringToAnagrams.putIfAbsent(sortedString, new ArrayList<>());

                stringToAnagrams.get(sortedString).add(s);
            }
            catch (IllegalArgumentException e)
            {
                System.err.println("Skipping invalid input: " + e.getMessage());
            }
            catch (Exception e)
            {
                System.err.println("Unexpected error while processing string " + s + ": " + e.getMessage() );
            }

        }

        List<List<String>> res = new ArrayList<>();

        for(List list : stringToAnagrams.values())
        {
            res.add(list);
        }

        return res;
    }

    public String sortString(String s)
    {
        char[] charArray = s.toCharArray();
        Arrays.sort(charArray);

        return new String(charArray);
    }
}
