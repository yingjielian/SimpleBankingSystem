package Jack2023;

import java.util.HashMap;

public class TwoSum {

    public static void main(String[] args)
    {
        twoSum(new int[]{3,4,5,6}, 7);
    }

    public static int[] twoSum(int[] nums, int target)
    {
        HashMap<Integer, Integer> map = new HashMap<>();

        for(int i = 0; i < nums.length; i++)
        {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++)
        {
            int reminder = target - nums[i];
            if(map.containsKey(reminder) && map.get(reminder) != i)
            {
                return new int[]{i, map.get(reminder)};
            }
        }

        return new int[]{};
    }

}
