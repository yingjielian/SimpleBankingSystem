package Jack2023;

import java.util.Arrays;

public class HasDuplicate {

    public static void main(String[] args)
    {

    }

    public static boolean hasDuplicate(int[] nums)
    {
        if(nums.length <= 1)
        {
            return false;
        }
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 1; i++)
        {
            if (nums[i] == nums[i+1])
            {
                return true;
            }
        }
        return false;
    }
}
