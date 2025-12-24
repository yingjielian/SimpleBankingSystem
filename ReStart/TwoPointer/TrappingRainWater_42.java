package Jack2025.ReStart.TwoPointer;

public class TrappingRainWater_42 {

    public int trap(int[] height)
    {
        int heighestBar = 0;
        int heighestBarIndex = 0;
        int total = 0;

        for(int i = 0; i < height.length; i++)
        {
            if(height[i] > heighestBar)
            {
                heighestBar = height[i];
                heighestBarIndex = i;
            }
        }

        // Loop from left -> right
        int leftHeighestBar = 0;
        for(int i = 0; i < heighestBarIndex; i++)
        {
            if(height[i] < leftHeighestBar)
            {
                total += leftHeighestBar - height[i];
            }
            else {
                leftHeighestBar = height[i];
            }
        }

        int rightHeighestBar = 0;
        for(int i = height.length - 1; i > heighestBarIndex; i--)
        {
            if(height[i] < rightHeighestBar)
            {
                total += rightHeighestBar - height[i];
            }
            else {
                rightHeighestBar = height[i];
            }
        }
        return total;
    }
}
