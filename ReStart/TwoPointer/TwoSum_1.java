package Jack2025.ReStart.TwoPointer;
import java.util.*;
public class TwoSum_1 {

    // Create a map
    // Check each num in nums for its complement with target, if the map contains the Key
    // return with [map.get(complement), current Index]
    // else, put the nums[i] as the key in map, value will be its index
    // return empty list otherwise
    public int[] twoSum(int[] nums, int target){

        Map<Integer, Integer> map = new HashMap<>();

        for(int i = 0; i < nums.length; i++)
        {
            int complement = target - nums[i]; // 9 - 2 = 7

            if(map.containsKey(complement))
            {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }

        return new int[]{};

    }
}
