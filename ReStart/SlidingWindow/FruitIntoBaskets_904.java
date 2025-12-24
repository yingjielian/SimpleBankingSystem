package Jack2025.ReStart.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

public class FruitIntoBaskets_904 {

    public int totalFruit(int[] fruits)
    {
        if(fruits.length == 0) return 0;
        if(fruits.length == 1) return 1;
        if(fruits.length == 2) return 2;

        int result = 0;
        Map<Integer, Integer> brasket = new HashMap<>();
        int start = 0;

        for(int end = 0; end < fruits.length; end++)
        {
            brasket.put(fruits[end], brasket.getOrDefault(fruits[end], 0) + 1);

            while(brasket.size() > 2)
            {
                brasket.put(fruits[start], brasket.get(fruits[start]) - 1);
                if(brasket.get(fruits[start]) == 0)
                {
                    brasket.remove(fruits[start]);
                }
                start++;
            }

            result = Math.max(result, end - start + 1);
        }

        return result;
    }
}
