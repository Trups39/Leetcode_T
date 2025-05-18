import java.util.*;

public class Main {
    /**
     * For each processor range [minPower[j], maxPower[j]],
     * returns [count, sum] of processes that lie in that range.
     */
    public static long[][] processExecution(
        int[] power,
        int[] minPower,
        int[] maxPower
    ) {
        int n = power.length, m = minPower.length;

        // 1) Sort a copy of power[]
        int[] pw = Arrays.copyOf(power, n);
        Arrays.sort(pw);

        // 2) Build prefix sums
        long[] pref = new long[n + 1];
        for (int i = 0; i < n; i++) {
            pref[i + 1] = pref[i] + pw[i];
        }

        // 3) For each processor, binary-search range
        long[][] ans = new long[m][2];
        for (int j = 0; j < m; j++) {
            int L = minPower[j], R = maxPower[j];
            int lo = lowerBound(pw, L);
            int hi = upperBound(pw, R) - 1;

            if (lo > hi) {
                ans[j][0] = 0;
                ans[j][1] = 0;
            } else {
                ans[j][0] = hi - lo + 1L;
                ans[j][1] = pref[hi + 1] - pref[lo];
            }
        }
        return ans;
    }

    // first index i where a[i] >= x
    private static int lowerBound(int[] a, int x) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] < x) lo = mid + 1;
            else            hi = mid;
        }
        return lo;
    }

    // first index i where a[i] > x
    private static int upperBound(int[] a, int x) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] <= x) lo = mid + 1;
            else             hi = mid;
        }
        return lo;
    }

    // --- Demo / Example Tests ---
    public static void main(String[] args) {
        // Example 1
        int[] power1 = {7, 6, 8, 10};
        int[] minP1  = {6, 3, 4};
        int[] maxP1  = {10,7, 9};
        System.out.println(Arrays.deepToString(
            processExecution(power1, minP1, maxP1)
        ));
        // expected [[4,31],[2,13],[3,21]]

        // Example 2
        int[] power2 = {11, 11, 11};
        int[] minP2  = {8, 13};
        int[] maxP2  = {11,100};
        System.out.println(Arrays.deepToString(
            processExecution(power2, minP2, maxP2)
        ));
        // expected [[3,33],[0,0]]

        // Edge: no processes
        System.out.println(Arrays.deepToString(
            processExecution(new int[0], new int[]{1}, new int[]{10})
        ));
        // expected [[0,0]]
    }
}
