package Jack2025.SOFI;

/*This code counts how many times each letter appears in both strings s and t using two frequency arrays of size 26 (one
        for each lowercase letter).
        It then sums the absolute differences between the two arrays to determine how many characters are mismatched
        between the strings.
        Finally, it divides the total by two because each replacement fixes one mismatch in both strings simultaneously,
        giving the minimum number of steps required to make them anagrams.*/
public class MinStepsAnagram_1347 {
    public static void main(String[] s)
    {
        System.out.println(minSteps("leetcode", "practice"));
    }

    public static int minSteps(String s, String t)
    {
        int countS[] = new int[26];
        int countT[] = new int[26];

        for(Character c : s.toCharArray())
        {
            countS[c - 'a']++;
        }

        for(Character c : t.toCharArray())
        {
            countT[c - 'a']++;
        }

        int steps = 0;
        for(int i = 0; i < 26; i++)
        {
            steps += Math.abs(countS[i] - countT[i]);
        }

        return steps/2;

    }

    // Faster
//    public static int minSteps(String s, String t)
//    {
//        int[] count = new int[26];
//
//        for(int i = 0; i < s.length(); i++)
//        {
//            count[s.charAt(i) - 'a']++;
//            count[t.charAt(i) - 'a']--;
//        }
//
//        int steps = 0;
//        for(Integer step : count)
//        {
//            if(step > 0)
//                steps += step;
//        }
//
//        return steps;
//    }

}
