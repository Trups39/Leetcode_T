import java.util.*;

public class Solution {
    /**
     * @param weights the original array of weights
     * @param k       the "skip‑k" parameter in operation 2
     * @return the lexicographically maximal result array
     */
    public static List<Integer> findMaximumWeights(int[] weights, int k) {
        int n = weights.length;
        // Build list of (weight, index) pairs
        List<int[]> pairs = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            pairs.add(new int[]{ weights[i], i });
        }
        // Sort by weight descending, then index ascending
        pairs.sort((a, b) -> {
            if (b[0] != a[0]) return b[0] - a[0];
            return a[1] - b[1];
        });

        List<Integer> result = new ArrayList<>();
        int nextAllowed = 0;      // the smallest index we may still pick
        for (int[] p : pairs) {
            int w = p[0], idx = p[1];
            if (idx >= nextAllowed) {
                result.add(w);
                nextAllowed = idx + k + 1;
            }
        }
        return result;
    }

    // —— Quick Test —— 
    public static void main(String[] args) {
        test(new int[]{4,3,5,5,3}, 1, Arrays.asList(5,3));
        test(new int[]{4,3,5,5,3}, 2, Arrays.asList(5,3));
        test(new int[]{4,3,5,5,3}, 3, Arrays.asList(5,3));
        test(new int[]{9,1,7,3,8,5}, 2, Arrays.asList(9,7,5));
        test(new int[]{1,2,3,4,5}, 1, Arrays.asList(5,4,3,2,1));
        test(new int[]{1,2,3,4,5}, 2, Arrays.asList(5,3,1));
        test(new int[]{1,2,3,4,5}, 3, Arrays.asList(5,2));
    }

    private static void test(int[] w, int k, List<Integer> exp) {
        List<Integer> got = findMaximumWeights(w, k);
        System.out.printf("weights=%s k=%d → %s (exp=%s)%n",
            Arrays.toString(w), k, got, exp);
    }
}
