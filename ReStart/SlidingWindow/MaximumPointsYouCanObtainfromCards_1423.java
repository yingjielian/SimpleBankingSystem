package Jack2025.ReStart.SlidingWindow;

public class MaximumPointsYouCanObtainfromCards_1423 {
    public int maxScore(int[] cardPoints, int k)
    {
        int result = 0;

        int total = 0;
        int length = cardPoints.length;
        for(int card : cardPoints)
        {
            total += card;
        }

        if(k >= length)
        {
            return total;
        }

        int windowLength = length - k;
        int state = 0;

        int start = 0;
        for(int end = 0; end < cardPoints.length; end++)
        {
            state += cardPoints[end];

            if(end - start + 1 == windowLength)
            {
                result = Math.max(result, total - state);
                state -= cardPoints[start];
                start++;
            }
        }

        return result;
    }
}
