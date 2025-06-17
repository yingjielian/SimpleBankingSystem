package Jack2025;

public class HasDuplicate {
    public boolean hasDuplicate(int[] nums)
    {
        for(int i = 0; i < nums.length; i++)
        {
            int current = nums[i];
            for(int j = i + 1; j < nums.length; j++)
            {
                if(current == nums[j])
                {
                    return true;
                }
            }
        }
        return false;
    }
}
