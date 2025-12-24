package Jack2025.ReStart.TwoPointer;

public class ContainerWithMostWater_11 {

    public int maxArea(int[] height)
    {
        int left = 0, right = height.length - 1;
        int currentMax = 0;

        while(left < right)
        {
            int length = right - left;
            int width = Math.min(height[right], height[left]);
            int area = length * width;
            currentMax = Math.max(currentMax, area);

            if(height[left] < height[right])
            {
                left++;
            }
            else{
                right--;
            }
        }

        return currentMax;
    }
}
