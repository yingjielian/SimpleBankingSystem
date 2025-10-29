package Jack2025.LinkedIn;

public class MaximumProductSubarray_152 {

    public int maxProduct(int[] nums)
    {
        int max = nums[0];
        int min = nums[0];
        int res = nums[0];

        for(int i = 1; i < nums.length; i++)
        {
            int storeMax = max;
            max = Math.max(Math.max(max * nums[i], min * nums[i]), nums[i]);
            min = Math.min(Math.min(storeMax * nums[i], min * nums[i]), nums[i]);
            res = Math.max(max, res);
        }

        return res;
    }
}
