package Jack2025.MongoDB;
import java.util.*;

//Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
//        Output: [[1,2],[3,10],[12,16]]
public class InsertInterval_57 {
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> list = new ArrayList<>();

        boolean added = false;

        for (int[] inter : intervals) {
            int maxStart = Math.max(inter[0], newInterval[0]);
            int minEnd = Math.min(inter[1], newInterval[1]);
            if (maxStart <= minEnd) {
                newInterval[0] = Math.min(inter[0], newInterval[0]);
                newInterval[1] = Math.max(inter[1], newInterval[1]);
            } else {
                if (inter[0] > newInterval[1] && !added) {
                    list.add(newInterval);
                    added = true;
                }
                list.add(inter);
            }
        }

        if (!added) {
            list.add(newInterval);
        }

        int[][] res = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }

        return res;
    }

    public static void main(String[] args) {
        // 测试输入
        int[][] intervals = {
                {1, 2},
                {3, 5},
                {6, 7},
                {8, 10},
                {12, 16}
        };
        int[] newInterval = {4, 8};

        // 调用 insert 方法
        int[][] result = insert(intervals, newInterval);

        System.out.println("Merged intervals:");
        for (int[] interval : result) {
            System.out.println(Arrays.toString(interval));
        }
    }
}
