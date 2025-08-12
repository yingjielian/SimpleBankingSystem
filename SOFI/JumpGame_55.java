package Jack2025.SOFI;

public class JumpGame_55 {
    /*This code keeps track of the farthest index (max) that can be reached while iterating through the array. At
    each  position i, it checks if i is beyond the current reachable range (max); if so, it returns false since you
    can’t proceed further. Otherwise, it updates max to the furthest reachable index from i, and if by the end max
    reaches or exceeds the last index, it returns true, indicating you can jump to the end.*/
    public boolean canJump(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > max) return false;
            int curr = i + nums[i];
            max = Math.max(max, curr);
        }

        return max >= nums.length - 1;
    }
}
