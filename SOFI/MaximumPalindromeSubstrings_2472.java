package Jack2025.SOFI;

/*Main catch here was to avoid picking a palindromic substring with size greater than k+1 because if there exist a
palindromic substring with size greater than k+1 then it would definitely contain a palindromic substring with size
at least k ( It was just basic observation ) And we are trying to minimize the length of the palindromic string
  ( with size >= k ) to maximize our answer.
Suppose,
string = abaczbzccc & k = 3

Without break condition, answer would have been 2 ( aba, czbzc )
With break condition, answer would be 3 ( aba, zbz, ccc )

Now observe the difference, instead of picking 'czbzc' we pick 'zbz' which allowed us to further pick 'ccc' as well.
I hope it clarifies your doubt.*/
public class MaximumPalindromeSubstrings_2472 {
    public int maxPalindromes(String s, int k) {
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                int len = (j - i) + 1;
                if (len > k + 1) break; // this is the key
                if (len >= k && isPalindrome(s, i, j)) {
                    ans++; i = j;  break;
                }
            }
        }
        return ans;
    }

    boolean isPalindrome(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l++) != s.charAt(r--))  return false;
        }
        return true;
    }
}
