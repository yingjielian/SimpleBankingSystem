package Jack2025.Karat;
import java.util.*;
public class CatchCheaters_1 {
    public static String containsWord(List<String> words, String scrambled) {
        int[] scrambledCount = getFrequencyMap(scrambled);

        for (String word : words) {
            int[] wordCount = getFrequencyMap(word);
            boolean found = true;
            for (int i = 0; i < 26; i++) {
                if (wordCount[i] > scrambledCount[i]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return word;
            }
        }
        return null;
    }

    private static int[] getFrequencyMap(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                count[c - 'a']++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(containsWord(Arrays.asList("cat", "baby", "dog", "bird", "car", "ax"), "tcabnihjs")); // "cat"
        System.out.println(containsWord(Arrays.asList("cat", "baby", "dog", "bird", "car", "ax"), "tbcanihjs")); // "cat"
        System.out.println(containsWord(Arrays.asList("cat", "baby", "dog", "bird", "car", "ax"), "baykkjl")); // null
        System.out.println(containsWord(Arrays.asList("cat", "baby", "dog", "bird", "car", "ax"), "bbabylkkj")); // "baby"
        System.out.println(containsWord(Arrays.asList("cat", "baby", "dog", "bird", "car", "ax"), "ccc")); // null
        System.out.println(containsWord(Arrays.asList("cat", "baby", "dog", "bird", "car", "ax"), "breadmaking")); // "bird"
    }
}
