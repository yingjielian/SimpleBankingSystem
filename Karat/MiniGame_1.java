package Jack2025.Karat;
import java.util.*;
public class MiniGame_1 {
    public static boolean complete(String tiles) {
        if (tiles == null || tiles.isEmpty()) {
            return false;
        }

        // Count the frequency of each digit
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : tiles.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        boolean hasPair = false;

        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            int count = entry.getValue();

            // Try to form triples first, then see if the remaining can form a pair
            while (count >= 3) {
                count -= 3;
            }

            // Check for pair
            if (count >= 2) {
                if (hasPair) {
                    // More than one pair is not allowed
                    return false;
                }
                hasPair = true;
                count -= 2;
            }

            // If there's any leftover tiles, it's invalid
            if (count != 0) {
                return false;
            }
        }

        return hasPair;
    }

    public static void main(String[] args) {
        String[] testCases = {
                "88844",    // True
                "99",       // True
                "55555",    // True
                "22333333", // True
                "73797439949499477339977777997394947947477993", // True
                "111333555", // False
                "42",       // False
                "888",      // False
                "100100000", // False
                "346664366", // False
                "8999998999898", // False
                "17610177", // False
                "600061166", // False
                "6996999",  // False
                "03799449", // False
                "64444333355556", // False
                "7",        // False
                "776655"    // False
        };

        for (String tiles : testCases) {
            System.out.println(complete(tiles) + " => " + tiles);
        }
    }
}
