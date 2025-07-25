package Jack2025.Karat;
import java.util.*;
public class WritingApplication_1 {
    public static List<String> wrapLines(List<String> words, int maxLength) {
        List<String> result = new ArrayList<>();
        if (words == null || words.isEmpty()) {
            return result;
        }

        int currentLineLength = 0;
        List<String> currentLine = new ArrayList<>();

        for (String word : words) {
            // Calculate potential new length:
            // current words length + new word length + (number of hyphens which is words count - 1)
            int potentialLength = currentLineLength + word.length();
            if (!currentLine.isEmpty()) {
                potentialLength += currentLine.size(); // for hyphens
            }

            if (potentialLength <= maxLength) {
                currentLine.add(word);
                currentLineLength += word.length();
            } else {
                // Add current line to result
                if (!currentLine.isEmpty()) {
                    result.add(String.join("-", currentLine));
                }
                // Start new line
                currentLine.clear();
                currentLine.add(word);
                currentLineLength = word.length();
            }
        }

        // Add the last line if not empty
        if (!currentLine.isEmpty()) {
            result.add(String.join("-", currentLine));
        }

        return result;
    }

    public static void main(String[] args) {
        // Test cases
        List<String> words1 = List.of("The", "day", "began", "as", "still", "as", "the",
                "night", "abruptly", "lighted", "with", "brilliant", "flame");
        System.out.println(wrapLines(words1, 13));
        System.out.println(wrapLines(words1, 20));

        List<String> words2 = List.of("Hello");
        System.out.println(wrapLines(words2, 5));

        List<String> words3 = List.of("Hello", "world");
        System.out.println(wrapLines(words3, 5));

        List<String> words4 = List.of("Well", "Hello", "world");
        System.out.println(wrapLines(words4, 5));

        List<String> words5 = List.of("Hello", "HelloWorld", "Hello", "Hello");
        System.out.println(wrapLines(words5, 20));
    }
}
