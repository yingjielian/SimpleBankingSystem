package Jack2025.LinkedIn;

public class MaxConsecutiveOnesIII_1004 {
    public int longestOnes(int[] nums, int k)
    {
        int low = 0;
        int prefixSum = 0;
        int result = 0;

        for(int high = 0; high < nums.length; high++)
        {
            if(nums[high] == 0)
            {
                prefixSum++;
            }

            if(prefixSum > k)
            {
                while(low < high && nums[low] == 1)
                {
                    low++;
                }
                prefixSum--;
                low++;
            }

            result = Math.max(result, high - low + 1);

        }

        return result;
    }
}
