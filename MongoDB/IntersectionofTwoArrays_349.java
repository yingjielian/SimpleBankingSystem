package Jack2025.MongoDB;
import java.util.*;
public class IntersectionofTwoArrays_349 {

    public int[] intersection(int[] nums1, int[] nums2)
    {
        Set<Integer> set = new HashSet<>();
        Set<Integer> dup = new HashSet<>();

        for(int i : nums1)
        {
            set.add(i);
        }

        for(int i : nums2)
        {
            if(set.contains(i))
            {
                dup.add(i);
            }
        }

        int[] res = new int[dup.size()];

        int i = 0;
        for(int num : dup)
        {
            res[i++] = num;
        }

        return res;
    }
}
