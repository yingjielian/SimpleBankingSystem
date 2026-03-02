package Jack2026.Nextdoor;

import java.util.*;

public class AdvancedVersionSorter implements Comparator<String> {
    @Override
    public int compare(String v1, String v2) {
        String[] mainPart1 = v1.split("-");
        String[] mainPart2 = v2.split("-");

        // 1. 比较基础数字部分
        String[] nums1 = mainPart1[0].split("\\.");
        String[] nums2 = mainPart2[0].split("\\.");
        int length = Math.max(nums1.length, nums2.length);

        for (int i = 0; i < length; i++) {
            int n1 = i < nums1.length ? Integer.parseInt(nums1[i]) : 0;
            int n2 = i < nums2.length ? Integer.parseInt(nums2[i]) : 0;
            if (n1 != n2) return n1 - n2;
        }

        // 2. 如果基础数字相同，比较 Pre-release 部分
        // 情况 A: 都有后缀，比字母序
        if (mainPart1.length > 1 && mainPart2.length > 1) {
            return mainPart1[1].compareTo(mainPart2[1]);
        }
        // 情况 B: 一个有后缀，一个没有 (有后缀的排在前面)
        if (mainPart1.length > 1) return -1;
        if (mainPart2.length > 1) return 1;

        return 0;
    }

    public static void main(String[] args) {
        List<String> versions = Arrays.asList("1.0.1-beta", "1.0.1-cat", "1.0.1", "6.3", "2.2.1");
        versions.sort(new AdvancedVersionSorter());
        System.out.println("Advanced Sort: " + versions);
        // 输出: [1.0.1-beta, 1.0.1-cat, 1.0.1, 2.2.1, 6.3]
    }
}
