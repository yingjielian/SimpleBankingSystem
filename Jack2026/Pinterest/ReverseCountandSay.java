package Jack2026.Pinterest;

import java.util.*;

class Solution {
    public List<String> findOriginals(String encoded) {
        List<String> res = new ArrayList<>();
        if (encoded == null || encoded.isEmpty()) return res;

        StringBuilder cur = new StringBuilder();
        dfs(encoded, 0, cur, res);
        return res;
    }

    private void dfs(String encoded, int idx, StringBuilder cur, List<String> res) {
        int n = encoded.length();
        if (idx == n) {
            // 必须验证：cur 经过一次 count-and-say 后，正好等于 encoded
            String original = cur.toString();
            if (encodeOnce(original).equals(encoded)) {
                res.add(original);
            }
            return;
        }

        // 选 1 位 count
        // 需要至少还有 2 个字符：count + digit
        if (idx + 1 < n) {
            char c0 = encoded.charAt(idx);
            if (c0 != '0') { // no leading zero, and count >= 1
                int count = c0 - '0';
                char digit = encoded.charAt(idx + 1);

                int oldLen = cur.length();
                appendRepeated(cur, digit, count);
                dfs(encoded, idx + 2, cur, res);
                cur.setLength(oldLen);
            }
        }

        // 选 2 位 count
        // 需要至少还有 3 个字符：2-digit count + digit
        if (idx + 2 < n) {
            char c0 = encoded.charAt(idx);
            char c1 = encoded.charAt(idx + 1);
            if (c0 != '0') { // no leading zero
                int count = (c0 - '0') * 10 + (c1 - '0');
                if (count >= 1 && count <= 99) {
                    char digit = encoded.charAt(idx + 2);

                    int oldLen = cur.length();
                    appendRepeated(cur, digit, count);
                    dfs(encoded, idx + 3, cur, res);
                    cur.setLength(oldLen);
                }
            }
        }
    }

    private void appendRepeated(StringBuilder sb, char ch, int times) {
        for (int i = 0; i < times; i++) sb.append(ch);
    }

    // 对 original 做一次经典 count-and-say 编码
    // 规则：读连续相同 digit，输出 count(十进制，可1~多位) + digit
    private String encodeOnce(String s) {
        if (s.isEmpty()) return "";
        StringBuilder out = new StringBuilder();
        int i = 0;
        int n = s.length();
        while (i < n) {
            char d = s.charAt(i);
            int j = i + 1;
            while (j < n && s.charAt(j) == d) j++;
            int cnt = j - i;
            out.append(cnt).append(d);
            i = j;
        }
        return out.toString();
    }

    // 可选：简单本地测试
    public static void main(String[] args) {
        Solution sol = new Solution();

        System.out.println(sol.findOriginals("1213"));   // ["23"]
        System.out.println(sol.findOriginals("12114"));  // ["244444444444", "1111111111114"] (顺序可能不同)
        System.out.println(sol.findOriginals("111213")); // ["123", "11111111111333333333333333333333"] (顺序可能不同)
    }
}

