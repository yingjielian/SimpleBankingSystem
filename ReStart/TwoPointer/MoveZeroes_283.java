package Jack2025.ReStart.TwoPointer;

public class MoveZeroes_283 {

    public void moveZeroes(int[] nums)
    {
        int nonZeroIndex = 0;

        // fill the array with all nonZero nums
        for(int i = 0; i < nums.length; i++)
        {
            if(nums[i] != 0)
            {
                nums[nonZeroIndex] = nums[i];
                nonZeroIndex++;
            }
        }

        // Fill all of the zeros after
        for(int i = nonZeroIndex + 1; i < nums.length; i++)
        {
            nums[i] = 0;
        }
    }

    // SWAP
//    public void moveZeroes(int[] nums)
//    {
//        int nextNonZero = 0;
//
//        for(int i = 0; i < nums.length; i++)
//        {
//            if(nums[i] != 0)
//            {
//                int temp = nums[nextNonZero];
//                nums[nextNonZero] = nums[i];
//                nums[i] = temp;
//                nextNonZero++;
//            }
//        }
//    }
}
