package Jack2025.LinkedIn;

public class MaximumSubarray_53 {

    public int maxSubArray(int[] nums)
    {
        int res = nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];

        for(int i = 1; i < nums.length; i++)
        {
            dp[i] = nums[i] + Math.max(dp[i - 1], 0);
            res = Math.max(res, dp[i]);
        }

        return res;
    }
}
