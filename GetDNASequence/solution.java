import java.util.*;

public class Solution {
    /**
     * For each pair of DNA strings, returns true if by deleting
     * all occurrences of at most one character in each string
     * (possibly different characters, or none), the two strings
     * can be made anagrams of one another.
     */
    public boolean[] getSequence(String[][] dna) {
        int n = dna.length;
        boolean[] ans = new boolean[n];
        for (int i = 0; i < n; i++) {
            ans[i] = similar(dna[i][0], dna[i][1]);
        }
        return ans;
    }

    private boolean similar(String s, String t) {
        // 1) Build frequency arrays
        int[] cntS = new int[26], cntT = new int[26];
        for (char c : s.toCharArray()) cntS[c - 'a']++;
        for (char c : t.toCharArray()) cntT[c - 'a']++;

        // 2) Collect all chars where counts differ
        List<Integer> diffs = new ArrayList<>(2);
        for (int c = 0; c < 26; c++) {
            if (cntS[c] != cntT[c]) {
                diffs.add(c);
                if (diffs.size() > 2) 
                    return false;  // too many mismatch types
            }
        }

        // 3) Check by casework on # of mismatched character‐types
        int m = diffs.size();
        if (m == 0) {
            // Already anagrams
            return true;
        }
        if (m == 1) {
            int x = diffs.get(0);
            // We can remove all of x from whichever string has it
            // iff one side's count is 0
            return cntS[x] == 0 || cntT[x] == 0;
        }
        // m == 2
        int a = diffs.get(0), b = diffs.get(1);
        // Must remove all a's from S and all b's from T, or vice versa
        boolean option1 = (cntS[a] > 0 && cntT[a] == 0)
                       && (cntT[b] > 0 && cntS[b] == 0);
        boolean option2 = (cntS[b] > 0 && cntT[b] == 0)
                       && (cntT[a] > 0 && cntS[a] == 0);
        return option1 || option2;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        String[][] dna1 = {{"safddadfs","famafmss"}};
        System.out.println(Arrays.toString(sol.getSequence(dna1)));
        // [true]

        String[][] dna2 = {
            {"abcee","acdeedb"},
            {"sljffsajej","sljsje"}
        };
        System.out.println(Arrays.toString(sol.getSequence(dna2)));
        // [true, false]

        // Edge cases:
        String[][] dna3 = {
            {"aab","aba"},  // already anagrams
            {"abc","abc"},  // identical
            {"aaa","bbb"},  // 1‐type each → remove 'a' and 'b'
            {"ab","cd"},    // two mismatches each → impossible
            {"a",""},       // remove 'a' → "" vs "" is anagram
        };
        System.out.println(Arrays.toString(sol.getSequence(dna3)));
        // [true, true, true, false, true]
    }
}
