package Jack2025.ReStart.SlidingWindow;

import java.util.*;

public class MaximumSumofDistinctSubarraysWithLengthK_2461 {

    public static long maximumSubarraySum(int[] nums, int k)
    {
        long result = 0;
        int start = 0;
        long sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();


        for(int end = 0; end < nums.length; end++)
        {
            sum += nums[end];
            map.put(nums[end], map.getOrDefault(nums[end], 0) + 1);

            if(end - start + 1 == k)
            {
                if(map.size() == k)
                {
                    result = Math.max(result, sum);
                }
                sum -= nums[start];
                map.put(nums[start], map.get(nums[start]) - 1);
                if(map.get(nums[start]) == 0)
                {
                    map.remove(nums[start]);
                }
                start++;
            }

        }


        return result;
    }

    public static void main(String[] args)
    {
        System.out.println(maximumSubarraySum(new int[]{1,5,4,2,9,9,9}, 3));
    }
}
