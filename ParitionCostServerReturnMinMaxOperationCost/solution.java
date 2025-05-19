import java.util.Arrays;

public class PartitionCostSort {
    /**
     * Returns [minCost, maxCost] for splitting `cost[]` into `k` contiguous segments.
     * Runs in O(n log n) time and O(n) extra space.
     */
    public static int[] findPartitionCost(int[] cost, int k) {
        int n = cost.length;
        if (k < 1 || k > n) throw new IllegalArgumentException("Invalid k");

        // Base cost: cost of the first segment's start + last segment's end
        long base = cost[0] + cost[n-1];
        if (k == 1) {
            // No cuts at all
            return new int[]{(int)base, (int)base};
        }

        // Build array of the n-1 "gap" costs:
        // cutting between i-1 and i adds cost[i-1] + cost[i]
        int[] gaps = new int[n-1];
        for (int i = 1; i < n; i++) {
            gaps[i-1] = cost[i-1] + cost[i];
        }

        // Sort ascending
        Arrays.sort(gaps);

        // Sum up the smallest (k-1) gaps for min, and largest (k-1) for max
        int m = k-1;
        long minSum = 0, maxSum = 0;
        int g = gaps.length;
        for (int i = 0; i < m; i++) {
            minSum += gaps[i];
            maxSum += gaps[g-1 - i];
        }

        return new int[]{ (int)(base + minSum), (int)(base + maxSum) };
    }

    // ——— Test Harness ———
    public static void main(String[] args) {
        run(new int[]{1,2,3,2,5}, 3, new int[]{14,18});
        run(new int[]{5,5,5,5},    2, new int[]{20,20});  // base=5+5=10, gap= 10 ⇒ 10+10=20
        run(new int[]{10,1,10},    1, new int[]{20,20});  // no cuts ⇒ base=10+10=20
        run(new int[]{4,9,2,6,3},  2, new int[]{15,20});  // gaps=[13,11,8,9] => min=8,max=13; base=4+3=7
    }

    private static void run(int[] cost, int k, int[] exp) {
        int[] ans = findPartitionCost(cost, k);
        System.out.printf("cost=%s k=%d → [min,max]=[%d,%d] (exp=[%d,%d])%n",
            Arrays.toString(cost), k, ans[0], ans[1], exp[0], exp[1]);
    }
}
---------------------------------------------------------------------------------------------------------------------------------------------------------------------
import java.util.*;

public class PartitionCostQuickselect {
    /**
     * Returns [minCost, maxCost] by selecting the k-1 smallest and largest gaps
     * via two Quickselect calls.  Average O(n), randomized.
     */
    public static int[] findPartitionCost(int[] cost, int k) {
        int n = cost.length;
        if (k < 1 || k > n) throw new IllegalArgumentException("Invalid k");

        long base = cost[0] + cost[n-1];
        if (k == 1) {
            return new int[]{(int)base, (int)base};
        }

        int g = n - 1, m = k - 1;
        int[] gaps = new int[g];
        for (int i = 1; i < n; i++) {
            gaps[i-1] = cost[i-1] + cost[i];
        }

        // 1) Quickselect to position the (m-1)th smallest gap
        quickSelect(gaps, 0, g-1, m-1);
        long minSum = 0;
        for (int i = 0; i < m; i++) minSum += gaps[i];

        // 2) Quickselect to position the (g-m)th slot for the m largest
        quickSelect(gaps, 0, g-1, g-m);
        long maxSum = 0;
        for (int i = g-m; i < g; i++) maxSum += gaps[i];

        return new int[]{(int)(base + minSum), (int)(base + maxSum)};
    }

    // Randomized Quickselect
    private static void quickSelect(int[] a, int l, int r, int pos) {
        if (l >= r) return;
        int pivot = l + (int)((r-l+1)*Math.random());
        pivot = partition(a, l, r, pivot);
        if (pos < pivot) {
            quickSelect(a, l, pivot-1, pos);
        } else if (pos > pivot) {
            quickSelect(a, pivot+1, r, pos);
        }
    }

    // Lomuto partition
    private static int partition(int[] a, int l, int r, int p) {
        int pv = a[p];
        swap(a, p, r);
        int store = l;
        for (int i = l; i < r; i++) {
            if (a[i] < pv) swap(a, store++, i);
        }
        swap(a, store, r);
        return store;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    // ——— Test Harness ———
    public static void main(String[] args) {
        run(new int[]{1,2,3,2,5}, 3, new int[]{14,18});
        run(new int[]{5,5,5,5},    2, new int[]{20,20});
        run(new int[]{10,1,10},    1, new int[]{20,20});
        run(new int[]{4,9,2,6,3},  2, new int[]{15,20});
    }

    private static void run(int[] cost, int k, int[] exp) {
        int[] ans = findPartitionCost(cost, k);
        System.out.printf("cost=%s k=%d → [min,max]=[%d,%d] (exp=[%d,%d])%n",
            Arrays.toString(cost), k, ans[0], ans[1], exp[0], exp[1]);
    }
}
