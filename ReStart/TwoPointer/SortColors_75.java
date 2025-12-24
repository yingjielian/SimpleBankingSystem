package Jack2025.ReStart.TwoPointer;

public class SortColors_75 {

    public void sortColors(int[] nums)
    {
        int index0 = 0, index2 = nums.length - 1, i = 0;

        while(i < index2)
        {
            if(nums[i] == 0)
            {
                swap(nums, i, index0);
                i++;
                index0++;
            }
            else if(nums[i] == 1)
            {
                i++;
            }
            else{
                swap(nums, i, index2);
                index2--;
            }
        }
    }

    public void swap(int[] nums, int a, int b)
    {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}
