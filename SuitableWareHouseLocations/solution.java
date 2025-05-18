import java.util.*;

public class Main {
    public static int suitableLocations(int[] center, long D) {
        int n = center.length;
        // 1) Sort a local copy
        int[] arr = Arrays.copyOf(center, n);
        Arrays.sort(arr);

        // 2) Build prefix sums
        long[] pref = new long[n + 1];
        for (int i = 0; i < n; i++) {
            pref[i + 1] = pref[i] + arr[i];
        }

        // Helper to compute F(x)=sum|x - arr[i]|
        // in O(log n) via binary search + prefix sums
        class Helper {
            long sumDist(int x) {
                // find count k of arr[i] <= x
                int k = Arrays.binarySearch(arr, x);
                if (k < 0) {
                    k = -k - 1;
                } else {
                    // if exact match, move to last occurrence
                    while (k + 1 < n && arr[k + 1] == x) k++;
                    k++;
                }
                long leftCost  = (long)x * k - pref[k];
                long rightCost = (pref[n] - pref[k]) - (long)x * (n - k);
                return leftCost + rightCost;
            }
        }
        Helper h = new Helper();

        // 3) Check best point at median
        int median = arr[n / 2];
        long best = h.sumDist(median);
        if (2 * best > D) {
            return 0;  // no x can satisfy
        }

        // 4) Binary-search leftmost x in [arr[0]..median]
        int low = arr[0], high = median, left = median;
        while (low <= high) {
            int mid = low + ((high - low) >>> 1);
            if (2 * h.sumDist(mid) <= D) {
                left = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        // 5) Binary-search rightmost x in [median..arr[n-1]]
        low = median; high = arr[n - 1];
        int right = median;
        while (low <= high) {
            int mid = low + ((high - low) >>> 1);
            if (2 * h.sumDist(mid) <= D) {
                right = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return Math.max(0, right - left + 1);
    }

    // --- Demo tests ---
    public static void main(String[] args) {
        // Example 1
        System.out.println(suitableLocations(new int[]{-2,1,0}, 8L));
        // expected 3  (x = -1, 0, 1)

        // Example 2
        System.out.println(suitableLocations(new int[]{2,0,3,-4}, 22L));
        // expected 5  (x = -1,0,1,2,3)

        // Example 3
        System.out.println(suitableLocations(new int[]{-3,2,2}, 8L));
        // expected 0

        // Edge: single center, zero budget
        System.out.println(suitableLocations(new int[]{100}, 0L));
        // expected 1  (only x=100)

        // Large d spans full min→max
        System.out.println(suitableLocations(new int[]{-5,0,5}, 2L*(5L+5L+0L)));
        // expected 11  (x from -5..5)
    }
}
