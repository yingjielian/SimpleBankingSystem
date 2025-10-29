package Jack2025.LinkedIn;

import java.util.*;

public class NestedListWeightSum_339 {

    public int depthSum(List<NestedInteger> nestedList)
    {
        int res = 0;

        Queue<NestedInteger> queue = new LinkedList<>(nestedList);

        int level = 1;
        while (!queue.isEmpty())
        {
            int size = queue.size();

            for(int i = 0; i < size; i++)
            {
                NestedInteger n = queue.poll();
                if(n.isInteger())
                {
                    res += n.getInteger() * level;
                }
                else {
                    List<NestedInteger> list = n.getList();
                    queue.addAll(list);
                }
            }
            level++;
        }
        return res;
    }
}
