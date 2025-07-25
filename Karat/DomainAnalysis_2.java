package Jack2025.Karat;
import java.util.*;
public class DomainAnalysis_2 {
    public static List<String> findContiguousHistory(String[] userA, String[] userB) {
        int m = userA.length;
        int n = userB.length;
        int[][] dp = new int[m + 1][n + 1];
        int maxLength = 0;
        int endIndex = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (userA[i - 1].equals(userB[j - 1])) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        endIndex = i - 1; // or j-1, since the sequence is the same in both arrays
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        List<String> result = new ArrayList<>();
        if (maxLength == 0) {
            return result;
        }

        for (int i = endIndex - maxLength + 1; i <= endIndex; i++) {
            result.add(userA[i]);
        }

        return result;
    }

    public static void main(String[] argv) {
        String[] user0 = {"/start", "/green", "/blue", "/pink", "/register", "/orange", "/one/two"};
        String[] user1 = {"/start", "/pink", "/register", "/orange", "/red", "a"};
        String[] user2 = {"a", "/one", "/two"};
        String[] user3 = {"/pink", "/orange", "/yellow", "/plum", "/blue", "/tan", "/red", "/amber", "/HotRodPink"};
        String[] user4 = {"/pink", "/orange", "/amber", "/BritishRacingGreen", "/plum", "/blue", "/tan", "/red", "/amber"};
        String[] user5 = {"a"};
        String[] user6 = {"/pink", "/orange", "/six", "/plum", "/seven", "/tan", "/red", "/amber"};

        System.out.println(findContiguousHistory(user0, user1)); // ["/pink", "/register", "/orange"]
        System.out.println(findContiguousHistory(user0, user2)); // []
        System.out.println(findContiguousHistory(user0, user0)); // full user0
        System.out.println(findContiguousHistory(user2, user1)); // ["a"]
        System.out.println(findContiguousHistory(user5, user2)); // ["a"]
        System.out.println(findContiguousHistory(user3, user4)); // ["/plum", "/blue", "/tan", "/red"]
        System.out.println(findContiguousHistory(user4, user3)); // ["/plum", "/blue", "/tan", "/red"]
        System.out.println(findContiguousHistory(user3, user6)); // ["/tan", "/red", "/amber"]
    }
}
