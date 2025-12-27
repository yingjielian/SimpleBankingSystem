package Jack2025.ReStart.Intervals;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeFreeTime_759 {

    public static int[][] employeeFreeTime(int[][][] schedule)
    {
        List<int[]> flatten = new ArrayList<>();

        for(int[][] employee : schedule)
        {
            for(int[] inter : employee)
            {
                flatten.add(inter);
            }
        }

        List<int[]> result = new ArrayList<>();

        Collections.sort(flatten, (a, b) -> a[0] - b[0]);

        List<int[]> merged = new ArrayList<>();

        int[] curr = flatten.get(0);

        for(int i = 1; i < flatten.size(); i++)
        {
            if(flatten.get(i)[0] > curr[1])
            {
                merged.add(curr);
                curr = flatten.get(i);
            }
            else {
                curr[1] = Math.max(curr[1], flatten.get(i)[1]);
            }
        }
        merged.add(curr);




        int[] currMerged = merged.get(0);
        for(int i = 1; i < merged.size(); i++)
        {
            int freeTimeStart = currMerged[1];
            int freeTimeEnd = merged.get(i)[0];

            result.add(new int[]{freeTimeStart, freeTimeEnd});
            currMerged = merged.get(i);
        }

        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args)
    {
        System.out.println(employeeFreeTime(new int[][][]{{{2,4}, {7,10}}, {{1,5}, {6,9}}}));
    }
}
