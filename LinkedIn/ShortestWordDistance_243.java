package Jack2025.LinkedIn;

public class ShortestWordDistance_243 {
    static public int findShortest(String[] words, String a, String b)
    {
        int index1 = -1;
        int index2 = -1;
        int result = Integer.MAX_VALUE;

        for(int i = 0; i < words.length; i++)
        {
            if(words[i].equals(a))
            {
                index1 = i;
            }
            else if(words[i].equals(b))
            {
                index2 = i;
            }

            if(index1 != -1 && index2 != -1)
            {
                result = Math.min(result, Math.abs(index1 - index2));
            }
        }

        return result;
    }

    public static void main(String[] args)
    {
        System.out.println(findShortest(new String[]{"practice", "makes", "perfect", "coding", "makes"}, "coding", "practice"));
        System.out.println(findShortest(new String[]{"practice", "makes", "perfect", "coding", "makes"}, "coding",
                "makes"));
    }
}
