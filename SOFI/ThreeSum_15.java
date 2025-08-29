package Jack2025.SOFI;

import java.util.*;
public class ThreeSum_15 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums == null || nums.length < 3) return res;

        Arrays.sort(nums);

        for(int i = 0; i < nums.length - 2; i++){
            // 去重
            if(i != 0 && nums[i] == nums[i - 1]) continue;
            int left = i + 1;
            int right = nums.length - 1;
            while(left < right){
                int sum = nums[i] + nums[left] + nums[right];
                if(sum == 0){
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 去重
                    while(left + 1 < right && nums[left + 1] == nums[left]) left++;
                    while(right - 1 > left && nums[right - 1] == nums[right]) right--;
                    left++;
                    right--;
                } else if (sum > 0){
                    right--;
                } else {
                    left++;
                }
            }
        }

        return res;
    }

}
