package Jack2025.Karat;
import java.util.*;
public class Cipher_2 {
    public static String encrypt(String message, String key) {
        // Create the cipher alphabet from the key
        Set<Character> uniqueLetters = new LinkedHashSet<>();
        for (char c : key.toUpperCase().toCharArray()) {
            if (Character.isLetter(c)) {
                uniqueLetters.add(c);
            }
        }

        // Create the standard alphabet
        char[] standardAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        // Build the substitution mapping (standard to cipher)
        char[] cipherAlphabet = new char[26];
        int index = 0;
        for (Character c : uniqueLetters) {
            if (index < 26) {
                cipherAlphabet[index++] = c;
            } else {
                break; // we only need 26 letters
            }
        }

        // If we didn't get all 26 letters, fill the rest with remaining alphabet letters
        // in order, excluding those already in the cipher alphabet
        if (index < 26) {
            for (char c = 'A'; c <= 'Z'; c++) {
                if (!uniqueLetters.contains(c)) {
                    cipherAlphabet[index++] = c;
                    if (index == 26) break;
                }
            }
        }

        // Create the encryption mapping
        char[] encryptionMap = new char[128]; // ASCII size for simplicity
        for (int i = 0; i < standardAlphabet.length; i++) {
            encryptionMap[standardAlphabet[i]] = cipherAlphabet[i];
            encryptionMap[Character.toLowerCase(standardAlphabet[i])] =
                    Character.toLowerCase(cipherAlphabet[i]);
        }

        // Encrypt the message
        StringBuilder encrypted = new StringBuilder();
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                encrypted.append(encryptionMap[c]);
            } else {
                encrypted.append(c); // leave non-letters unchanged
            }
        }

        return encrypted.toString();
    }

    public static void main(String[] args) {
        String key = "The quick onyx goblin, Grabbing his sword ==}-------- jumps over the 1st lazy dwarf!";

        System.out.println(encrypt("It was all a dream.", key));
        // Expected: "Od ptw txx t qsutg."

        System.out.println(encrypt("Would you kindly?", key));
        // Expected: "Pljxq zlj yobqxz?"
    }
}
