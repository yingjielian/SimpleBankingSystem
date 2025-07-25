package Jack2025.Karat;
import java.util.*;
public class Cipher_3 {

    public static Set<String> decrypt(List<String> dictionary, String ciphertext) {
        Set<String> result = new HashSet<>();
        backtrack(dictionary, ciphertext, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(List<String> dictionary, String ciphertext, int index,
                                  List<String> current, Set<String> result) {
        if (index == ciphertext.length()) {
            // Check if all parts are valid words
            for (String part : current) {
                if (!isValidWord(dictionary, part.length(), countUniqueDigits(part))) {
                    return;
                }
            }
            // If valid, add all possible words for each part
            generateCombinations(dictionary, current, 0, new StringBuilder(), result);
            return;
        }

        // Try single digit
        if (index < ciphertext.length()) {
            String singleDigit = ciphertext.substring(index, index + 1);
            if (isValidDigit(singleDigit)) {
                current.add(singleDigit);
                backtrack(dictionary, ciphertext, index + 1, current, result);
                current.remove(current.size() - 1);
            }
        }

        // Try double digits (if possible)
        if (index + 1 < ciphertext.length()) {
            String doubleDigits = ciphertext.substring(index, index + 2);
            if (isValidDigit(doubleDigits) && Integer.parseInt(doubleDigits) <= 26) {
                current.add(doubleDigits);
                backtrack(dictionary, ciphertext, index + 2, current, result);
                current.remove(current.size() - 1);
            }
        }
    }

    private static boolean isValidDigit(String s) {
        try {
            int num = Integer.parseInt(s);
            return num >= 1 && num <= 26;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int countUniqueDigits(String s) {
        Set<Character> unique = new HashSet<>();
        for (char c : s.toCharArray()) {
            unique.add(c);
        }
        return unique.size();
    }

    private static boolean isValidWord(List<String> dictionary, int length, int uniqueDigits) {
        for (String word : dictionary) {
            if (word.length() == length && countUniqueLetters(word) == uniqueDigits) {
                return true;
            }
        }
        return false;
    }

    private static int countUniqueLetters(String word) {
        Set<Character> unique = new HashSet<>();
        for (char c : word.toCharArray()) {
            unique.add(c);
        }
        return unique.size();
    }

    private static void generateCombinations(List<String> dictionary, List<String> parts,
                                             int partIndex, StringBuilder sb, Set<String> result) {
        if (partIndex == parts.size()) {
            result.add(sb.toString());
            return;
        }

        String part = parts.get(partIndex);
        int length = part.length();
        int uniqueDigits = countUniqueDigits(part);

        for (String word : dictionary) {
            if (word.length() == length && countUniqueLetters(word) == uniqueDigits) {
                int prevLength = sb.length();
                if (partIndex > 0) sb.append(", ");
                sb.append(word);
                generateCombinations(dictionary, parts, partIndex + 1, sb, result);
                sb.setLength(prevLength);
            }
        }
    }

    public static void main(String[] args) {
        List<String> dictionary1 = List.of("AXE", "CAT", "AT", "OR", "A", "COO", "CARD");
        System.out.println(decrypt(dictionary1, "123"));  // AXE, CAT, AT, OR
        System.out.println(decrypt(dictionary1, "122"));  // COO, AT, OR
        System.out.println(decrypt(dictionary1, "102"));  // AT, OR
        System.out.println(decrypt(dictionary1, "1021")); // AXE, CAT, AT, OR
        System.out.println(decrypt(dictionary1, "1105")); // AXE, CAT

        List<String> dictionary2 = List.of("BOXY", "BORN", "FORTH", "FROTH", "ARTERY", "ACES", "PORTO", "THOR");
        System.out.println(decrypt(dictionary2, "10251826")); // ARTERY, ACES, THOR, BORN, BOXY, FORTH, FROTH
    }
}
