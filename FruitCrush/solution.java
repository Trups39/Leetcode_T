import java.util.*;

public class Solution {
    /**
     * Returns the minimum number of fruits left after repeatedly
     * crushing any two distinct fruit‐types.
     */
    public static int getMinimumFruits(int[] fruits) {
        int n = fruits.length;
        // Count frequencies
        Map<Integer,Integer> freq = new HashMap<>();
        int maxFreq = 0;
        for (int f : fruits) {
            int c = freq.getOrDefault(f, 0) + 1;
            freq.put(f, c);
            maxFreq = Math.max(maxFreq, c);
        }

        int rest = n - maxFreq;
        if (maxFreq > rest) {
            // majority outlives all others
            return maxFreq - rest;
        } else {
            // everything can be paired down to at most one if n odd
            return n & 1;
        }
    }

    public static void main(String[] args) {
        // Example 1
        System.out.println(getMinimumFruits(new int[]{3,3,1,1,2}));
        // → 1
        // Explanation: crush (1,2)->[3,3,1,1], then (1,3)->[3,1], then (1,3)->[3]

        // Example 2: all distinct
        System.out.println(getMinimumFruits(new int[]{1,2,3,4}));
        // → 0

        // Example 3: all same
        System.out.println(getMinimumFruits(new int[]{5,5,5,5}));
        // → 4

        // Example 4: majority just barely
        System.out.println(getMinimumFruits(new int[]{7,7,7,2,3}));
        // M=3, rest=2 → leftover=1

        // Example 5: mix
        System.out.println(getMinimumFruits(new int[]{8,9,8,9,10}));
        // n=5, M=2, rest=3 → n%2=1 → leftover=1
    }
}
