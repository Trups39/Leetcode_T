import java.util.*;

public class Solution {
    /**
     * Returns [maximumMedian, minimumMedian] among all subsequences
     * of length k, using two heaps.
     */
    public static int[] mediansWithHeaps(int[] values, int k) {
        int n = values.length;
        int idx = (k - 1) / 2;

        // —— Compute maximum median ——
        // Keep a min‑heap of the k largest elements
        PriorityQueue<Integer> minH = new PriorityQueue<>();
        for (int x : values) {
            if (minH.size() < k) {
                minH.offer(x);
            } else if (x > minH.peek()) {
                minH.poll();
                minH.offer(x);
            }
        }
        // Pop idx times so the root is the lower median
        for (int i = 0; i < idx; i++) {
            minH.poll();
        }
        int maxMedian = minH.peek();

        // —— Compute minimum median ——
        // Keep a max‑heap of the k smallest elements
        PriorityQueue<Integer> maxH = new PriorityQueue<>(Comparator.reverseOrder());
        for (int x : values) {
            if (maxH.size() < k) {
                maxH.offer(x);
            } else if (x < maxH.peek()) {
                maxH.poll();
                maxH.offer(x);
            }
        }
        // Number of pops to discard the largest (k‑1 − idx) elements above idx
        int pops2 = (k - 1) - idx;
        for (int i = 0; i < pops2; i++) {
            maxH.poll();
        }
        int minMedian = maxH.peek();

        return new int[]{maxMedian, minMedian};
    }

    // ——— Test Harness ———
    public static void main(String[] args) {
        runTest(new int[]{1, 2, 3},       2, new int[]{2, 1});
        runTest(new int[]{5, 1, 4, 3, 2}, 3, new int[]{4, 2});
        runTest(new int[]{7, 7, 7, 7},    4, new int[]{7, 7});
        runTest(new int[]{10,20,30,40,50},1, new int[]{50,10});
        runTest(new int[]{8, 6, 5, 3, 1}, 2, new int[]{6, 1});
        runTest(new int[]{9,8,7,6,5,4,3,2,1}, 3, new int[]{7, 3});
    }

    private static void runTest(int[] values, int k, int[] expected) {
        int[] res = mediansWithHeaps(values.clone(), k);
        System.out.printf(
          "values=%s, k=%d → [maxMed,minMed]=[%d,%d]  (exp=[%d,%d])%n",
          Arrays.toString(values), k,
          res[0], res[1], expected[0], expected[1]
        );
    }
}
--------------------------------------------------------------------------------------------------------------------------------------
import java.util.*;

public class Solution {
    /**
     * Returns [maximumMedian, minimumMedian] among all subsequences of length k,
     * using two heaps:
     *  - a min‐heap to track the k largest elements for the maxMedian
     *  - a max‐heap to track the k smallest elements for the minMedian
     */
    public static int[] mediansWithHeaps(int[] values, int k) {
        int idx = (k - 1) / 2;

        // 1) Compute maximum median via a min‐heap of the k largest values
        PriorityQueue<Integer> minH = new PriorityQueue<>();
        for (int x : values) {
            if (minH.size() < k) {
                minH.offer(x);
            } else if (x > minH.peek()) {
                minH.poll();
                minH.offer(x);
            }
        }
        // Pop idx smallest among those k so that the root is the lower median
        for (int i = 0; i < idx; i++) {
            minH.poll();
        }
        int maxMedian = minH.peek();

        // 2) Compute minimum median via a max‐heap of the k smallest values
        PriorityQueue<Integer> maxH = new PriorityQueue<>(Comparator.reverseOrder());
        for (int x : values) {
            if (maxH.size() < k) {
                maxH.offer(x);
            } else if (x < maxH.peek()) {
                maxH.poll();
                maxH.offer(x);
            }
        }
        // Pop the largest (k−1−idx) of those k so that the root is the lower median
        int pops2 = (k - 1) - idx;
        for (int i = 0; i < pops2; i++) {
            maxH.poll();
        }
        int minMedian = maxH.peek();

        return new int[]{maxMedian, minMedian};
    }

    // ——— Test Harness ———
    public static void main(String[] args) {
        runTest(new int[]{1, 2, 3},                2, new int[]{2, 1});
        runTest(new int[]{5, 1, 4, 3, 2},          3, new int[]{4, 2});
        runTest(new int[]{7, 7, 7, 7},             4, new int[]{7, 7});
        runTest(new int[]{10, 20, 30, 40, 50},     1, new int[]{50, 10});
        runTest(new int[]{8, 6, 5, 3, 1},          2, new int[]{6, 1});
        runTest(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1}, 3, new int[]{8, 2});
        // edge cases
        runTest(new int[]{42},                     1, new int[]{42, 42});
        runTest(new int[]{2, 9, 5, 7, 3},          5, new int[]{5, 5});
        runTest(new int[]{100, 1, 50, 75, 25, 80},  3, new int[]{75, 50});
    }

    private static void runTest(int[] values, int k, int[] expected) {
        int[] result = mediansWithHeaps(values, k);
        System.out.printf("values=%s, k=%d → [maxMed,minMed]=[%d,%d] (exp=[%d,%d])%n",
            Arrays.toString(values), k,
            result[0], result[1],
            expected[0], expected[1]);
    }
}
