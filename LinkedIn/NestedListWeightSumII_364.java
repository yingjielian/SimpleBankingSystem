package Jack2025.LinkedIn;
import java.util.*;
public class NestedListWeightSumII_364 {
    public static int depthSumInverse(List<NestedInteger> nestedList)
    {
        int sum = 0;
        int extra = 0;

        Queue<NestedInteger> queue = new LinkedList<>(nestedList);

        int level = 1;
        while(!queue.isEmpty())
        {
            int size = queue.size();
            for (int i = 0; i < size; i++)
            {
                NestedInteger curr = queue.poll();
                if(curr.isInteger())
                {
                    sum += curr.getInteger();
                    extra += level * curr.getInteger();
                }
                else {
                    queue.addAll(curr.getList());
                }
            }
            level++;
        }
        return sum * level - extra;

    }

}
