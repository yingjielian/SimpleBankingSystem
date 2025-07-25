package Jack2025.Karat;

import java.util.*;
public class WritingApplication_2 {
    public static List<String> reflowAndJustify(List<String> lines, int maxWidth) {
        // Step 1: Split all lines into individual words
        List<String> words = new ArrayList<>();
        for (String line : lines) {
            String[] lineWords = line.split(" ");
            for (String word : lineWords) {
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }
        }

        // Step 2: Reflow words into lines of maxWidth
        List<String> reflowedLines = new ArrayList<>();
        List<String> currentLine = new ArrayList<>();
        int currentLength = 0;

        for (String word : words) {
            // Check if adding the word would exceed maxWidth
            int potentialLength = currentLength + word.length();
            if (!currentLine.isEmpty()) {
                potentialLength += 1; // Account for at least one hyphen
            }

            if (potentialLength <= maxWidth) {
                currentLine.add(word);
                currentLength = potentialLength;
            } else {
                // Add current line to reflowedLines
                reflowedLines.add(justifyLine(currentLine, maxWidth));
                // Start new line with current word
                currentLine.clear();
                currentLine.add(word);
                currentLength = word.length();
            }
        }

        // Add the last line (don't justify if it's a single word)
        if (!currentLine.isEmpty()) {
            if (currentLine.size() == 1) {
                reflowedLines.add(currentLine.get(0));
            } else {
                reflowedLines.add(justifyLine(currentLine, maxWidth));
            }
        }

        return reflowedLines;
    }

    private static String justifyLine(List<String> words, int maxWidth) {
        if (words.size() == 1) {
            return words.get(0);
        }

        int totalWordsLength = 0;
        for (String word : words) {
            totalWordsLength += word.length();
        }

        int totalSpaces = maxWidth - totalWordsLength;
        int gaps = words.size() - 1;
        int baseSpaces = totalSpaces / gaps;
        int extraSpaces = totalSpaces % gaps;

        StringBuilder justifiedLine = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            justifiedLine.append(words.get(i));
            if (i < gaps) {
                // Add base spaces
                for (int j = 0; j < baseSpaces; j++) {
                    justifiedLine.append('-');
                }
                // Add extra spaces if any remain
                if (extraSpaces > 0) {
                    justifiedLine.append('-');
                    extraSpaces--;
                }
            }
        }

        return justifiedLine.toString();
    }

    public static void main(String[] args) {
        List<String> lines = List.of(
                "The day began as still as the",
                "night abruptly lighted with",
                "brilliant flame"
        );

        // Test cases
        System.out.println(reflowAndJustify(lines, 24));
        System.out.println(reflowAndJustify(lines, 25));
        System.out.println(reflowAndJustify(lines, 26));
        System.out.println(reflowAndJustify(lines, 40));
        System.out.println(reflowAndJustify(lines, 14));
    }
}
