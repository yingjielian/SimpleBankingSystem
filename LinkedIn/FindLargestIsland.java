package Jack2025.LinkedIn;

public class FindLargestIsland {

    public static int findLargest(int[] island, int material)
    {
        if(island == null || island.length == 0) return 0;
        if(material < 0) material = 0;

        int left = 0, zeros = 0, maxLen = 0;

        for(int right = 0; right < island.length; right++)
        {
            if(island[right] == 0)
            {
                zeros++;
            }

            while(zeros > material)
            {
                if(island[left] == 0)
                {
                    zeros--;
                }
                left++;
            }
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }

    // Follow-up question, what if the given island array is a circular array, the n-1th item connected
    // with the 0 index item.
    public static int findCircular(int[] island, int material)
    {
        if(island == null || island.length == 0) return 0;

        int n = island.length;

        // Build circular simulation: A + A
        int[] doubledIsland = new int[n * 2];

        for (int i = 0; i < n; i++)
        {
            doubledIsland[i] = island[i];
            doubledIsland[i + n] = island[i];
        }

        int result = findLargest(doubledIsland, material);

        return Math.min(result, n);
    }

    public static void main(String[] args)
    {
        System.out.println(findLargest(new int[]{1,0,0,0,1}, 3));
        System.out.println(findLargest(new int[]{0,1,0,0,0,1,1}, 2)); // 4
        System.out.println(findLargest(new int[]{1,1,0,0,0,1,1,1}, 1)); // 4
        System.out.println(findLargest(new int[]{}, 2));
        System.out.println(findLargest(new int[]{1}, 2));
        System.out.println(findLargest(null, 2));

        System.out.println(findCircular(new int[]{0,1,0,0,0,1,1}, 2)); // 5
        System.out.println(findCircular(new int[]{1,1,0,0,0,1,1,1}, 1)); // 6
    }
}
