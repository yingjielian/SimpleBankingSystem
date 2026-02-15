package Jack2026.OKX;

public class CountSubarraysWithFixedBounds_2444 {

//    Input: nums = [1,3,5,2,7,5], minK = 1, maxK = 5
//    Output: 2
//    Explanation: The fixed-bound subarrays are [1,3,5] and [1,3,5,2].
//    Example 2:
//
//    Input: nums = [1,1,1,1], minK = 1, maxK = 1
//    Output: 10
//    Explanation: Every subarray of nums is a fixed-bound subarray. There are 10 possible subarrays.
    public static long countSubarrays(int[] nums, int minK, int maxK)
    {
        long res = 0;
        int n = nums.length;

        for(int i = 0; i < n; i++)
        {
            int curMin = Integer.MAX_VALUE;
            int curMax = Integer.MIN_VALUE;
            for(int j = i; j < n; j++)
            {
                curMin = Math.min(curMin, nums[j]);
                curMax = Math.max(curMax, nums[j]);

                if(curMin == minK && curMax == maxK)
                {
                    res++;
                }

                if(nums[j] > maxK || nums[j] < minK) break;
            }
        }
        return res;
    }

    public static void main(String[] args)
    {
        System.out.println(countSubarrays(new int[]{1,3,5,2,7,5}, 1, 5));
    }
}
