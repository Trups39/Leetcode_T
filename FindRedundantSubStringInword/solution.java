import java.util.*;

public class Solution {

    private static final Set<Character> VOWELS = Set.of('a','e','i','o','u');

    public static long getRedundantSubstrings(String word, int a, int b) {
        int n = word.length();

        // all substrings redundant
        if (a == b) {
            return (a == 1) ? (long) n * (n + 1) / 2 : 0L;
        }

        long diffAB = a - (long) b;   // (a-b)
        long diff1B = 1L - b;         // (1-b)

        long prefixV = 0;             // running vowel count
        Map<Long, Long> freq = new HashMap<>(n * 2);
        freq.put(0L, 1L);             // empty prefix
        long ans = 0;

        for (int k = 1; k <= n; k++) {
            if (VOWELS.contains(word.charAt(k - 1))) {
                prefixV++;
            }
            long key = diffAB * prefixV - diff1B * k;

            long count = freq.getOrDefault(key, 0L);
            ans += count;
            freq.put(key, count + 1);
        }

        return ans;
    }

    /* ---------- simple driver ---------- */
    public static void main(String[] args) {
        System.out.println(getRedundantSubstrings("abbacc", -1, 2)); // 5
        System.out.println(getRedundantSubstrings("akljfs", -2, 1)); // 15
        System.out.println(getRedundantSubstrings("abc", 1, 1));     // 6
        System.out.println(getRedundantSubstrings("aeiou", 3, 3));   // 0
    }
}
