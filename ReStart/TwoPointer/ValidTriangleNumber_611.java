package Jack2025.ReStart.TwoPointer;

import java.util.Arrays;

public class ValidTriangleNumber_611 {

    public int triangleNumber(int[] nums)
    {
        if(nums == null || nums.length < 3) return 0;

        int result = 0;
        Arrays.sort(nums);

        for(int i = nums.length - 1; i >= 2; i--)
        {
            int left = 0;
            int right = i - 1;

            while(left < right)
            {
                if(nums[left] + nums[right] > nums[i])
                {
                    result += right - left;
                    right--;
                }
                else
                {
                    left++;
                }

            }
        }
        return result;
    }
}
