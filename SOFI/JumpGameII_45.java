package Jack2025.SOFI;

public class JumpGameII_45 {
    /*This code tracks the minimum number of jumps needed to reach the last index by iterating through the array and
     updating the farthest reachable position (currFarthest) from the current index. Whenever the current index
     reaches  the end of the range covered by the previous jump (currEnd), it increments the jump count and updates
     currEnd to currFarthest to mark the new jump range. By the end of the loop, the total jumps represent the
     minimum jumps required to reach the last position.*/
    public int jump(int[] nums) {
        int jumps = 0;

        int currEnd = 0;
        int currFarthest = 0;

        // [2,3,1,1,4]
        for (int i = 0; i < nums.length - 1; i++) {
            currFarthest = Math.max(currFarthest, i + nums[i]);

            if (i == currEnd) {
                jumps++;
                currEnd = currFarthest;
            }
        }

        return jumps;
    }
}
