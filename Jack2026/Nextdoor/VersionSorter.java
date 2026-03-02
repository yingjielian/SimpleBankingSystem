package Jack2026.Nextdoor;

import java.util.*;

public class VersionSorter implements Comparator<String> {
    @Override
    public int compare(String v1, String v2) {
        String[] parts1 = v1.split("\\.");
        String[] parts2 = v2.split("\\.");

        int length = Math.max(parts1.length, parts2.length);
        for (int i = 0; i < length; i++) {
            // 如果长度不足，补0进行比较（例如 2.3 视为 2.3.0）
            int num1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
            int num2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;

            if (num1 != num2) {
                return num1 - num2;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        List<String> versions = Arrays.asList("1.0.1", "2.3", "2.2.1");
        versions.sort(new VersionSorter());
        System.out.println("Basic Sort: " + versions);
        // 输出: [1.0.1, 2.2.1, 2.3]
    }
}