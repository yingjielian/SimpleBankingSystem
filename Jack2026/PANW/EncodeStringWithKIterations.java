package Jack2026.PANW;

public class EncodeStringWithKIterations {

    public static String encodeStringWithKIterations(String input, int k) {
        if (input == null || input.length() == 0 || k <= 0) {
            return "";
        }

        String result = input;

        for (int i = 0; i < k; i++) {
            result = encodeOnce(result);
        }

        return result;
    }

    private static String encodeOnce(String s) {
        StringBuilder sb = new StringBuilder();

        int count = 1;
        for (int i = 1; i <= s.length(); i++) {
            if (i < s.length() && s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                sb.append(count).append(s.charAt(i - 1));
                count = 1;
            }
        }

        return sb.toString();
    }

    // test
    public static void main(String[] args) {
        System.out.println(encodeStringWithKIterations("1123", 3));
        System.out.println(encodeStringWithKIterations("12345", 0));
        System.out.println(encodeStringWithKIterations("a", 2));
        System.out.println(encodeStringWithKIterations("11122", 4));
        // expected: 112221123113
    }
}
