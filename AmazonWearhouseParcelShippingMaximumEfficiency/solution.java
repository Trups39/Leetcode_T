import java.util.*;

/**
 * Amazon OA  –  “Warehouse Efficiency”
 * -------------------------------------
 * Every day the first and last parcels are shipped.  *Before* the two parcels
 * are removed we may add the weight of **one** still–present parcel to the
 * running total.  A parcel may be counted at most once.  What is the maximum
 * possible total?
 *
 * Observation
 * -----------
 * Let n be the number of parcels.  In day d (0‑indexed) the active sub‑array
 * is [d , n‑1‑d].  So there are `days = ceil(n/2)` shipping rounds and we may
 * choose at most one parcel per round.
 *
 * If we walk from the *last* day to the first while maintaining a max‑heap
 * (priority queue) of all parcels that have become visible so far, always
 * taking the largest available weight, we obtain an optimal schedule:
 *   - On the last day only the middle one/two parcels are visible → choose the
 *     larger of those.
 *   - On the previous day two new boundary parcels become visible; add them to
 *     the heap and again take the current maximum that has not been used yet.
 *   - Repeat until day 0.
 *
 * Each parcel is pushed into the heap exactly once and popped at most once:
 *   Time  :  O(n log n)
 *   Memory:  O(n)
 */
public class MaxWarehouseEfficiency {

    public static long getMaxEfficiency(int[] w) {
        int n = w.length;
        int days = (n + 1) >> 1;                 // ceil(n/2)
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        boolean[] used = new boolean[n];         // a parcel may only be taken once

        long ans = 0;
        for (int d = days - 1; d >= 0; d--) {
            int l = d;
            int r = n - 1 - d;

            // add the two boundary parcels that *become* visible today
            pq.offer(w[l]);                       // left boundary always exists
            if (l != r) pq.offer(w[r]);           // right boundary (unless same)

            // take the current maximum weight that has not been picked yet
            int best = pq.poll();                 // non‑null by construction
            ans += best;
        }
        return ans;
    }

    // ------------------------------------------------------------
    // simple driver with the three sample test‑cases
    public static void main(String[] args) {
        int[][] tests = {
            {4, 4, 8, 5, 3, 2},
            {2, 1, 8, 5, 6, 2, 4},
            {1, 5, 5, 2}
        };
        for (int[] t : tests) {
            System.out.println(Arrays.toString(t) + "  ->  " + getMaxEfficiency(t));
        }
        // expected: 17, 23, 10
    }
}
----------------------------------------------------------------------------------
