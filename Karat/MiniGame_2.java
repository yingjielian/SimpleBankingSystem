package Jack2025.Karat;
import java.util.*;
public class MiniGame_2 {
    public static boolean advanced(String hand) {
        if (hand == null || hand.isEmpty()) {
            return false;
        }

        // Count the frequency of each tile
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : hand.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        // Try every possible pair
        for (char pairTile : freq.keySet()) {
            if (freq.get(pairTile) < 2) {
                continue;
            }

            // Make a copy of the frequency map to avoid modifying the original
            Map<Character, Integer> tempFreq = new HashMap<>(freq);
            tempFreq.put(pairTile, tempFreq.get(pairTile) - 2);

            if (isValidHand(tempFreq)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isValidHand(Map<Character, Integer> freq) {
        // Create a new copy to manipulate
        Map<Character, Integer> tempFreq = new HashMap<>(freq);

        // Process triples first
        for (char tile : tempFreq.keySet()) {
            while (tempFreq.get(tile) >= 3) {
                tempFreq.put(tile, tempFreq.get(tile) - 3);
            }
        }

        // Process runs
        for (char tile = '0'; tile <= '9'; tile++) {
            while (tempFreq.containsKey(tile) && tempFreq.get(tile) > 0) {
                // Check if the next two consecutive tiles exist and have at least one count
                char next1 = (char) (tile + 1);
                char next2 = (char) (tile + 2);
                if (next2 > '9') {
                    break; // invalid run as it would wrap around (e.g., 890)
                }
                if (tempFreq.containsKey(next1) && tempFreq.get(next1) > 0 &&
                        tempFreq.containsKey(next2) && tempFreq.get(next2) > 0) {
                    // Form the run
                    tempFreq.put(tile, tempFreq.get(tile) - 1);
                    tempFreq.put(next1, tempFreq.get(next1) - 1);
                    tempFreq.put(next2, tempFreq.get(next2) - 1);
                } else {
                    break;
                }
            }
        }

        // Check if all tiles are used up
        for (int count : tempFreq.values()) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String[] testCases = {
                "11123",    // True
                "12131",    // True
                "11123455", // True
                "11122334", // True
                "11234",    // True
                "123456",   // False
                "111333555777", // True
                "11223344556677", // True
                "12233444556677", // True
                "1123456789", // False
                "00123457", // False
                "0012345",  // False
                "11890",    // False
                "99",       // True
                "11122344"  // False
        };

        for (String hand : testCases) {
            System.out.println(advanced(hand) + " => " + hand);
        }
    }
}
