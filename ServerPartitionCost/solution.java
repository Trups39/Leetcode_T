import java.util.*;

public class ServerChainsHeaps {
    public static long[] minMaxCost(int[] cost, int k) {
        int n = cost.length;
        long base = (long)cost[0] + cost[n-1];
        if (k == 1) {
            return new long[]{ base, base };
        }

        // Max-heap to keep the k-1 smallest w[i]
        PriorityQueue<Long> small = new PriorityQueue<>(k, Collections.reverseOrder());
        // Min-heap to keep the k-1 largest  w[i]
        PriorityQueue<Long> large = new PriorityQueue<>(k);

        for (int i = 0; i < n - 1; i++) {
            long w = (long)cost[i] + cost[i+1];

            // maintain small = k-1 smallest w’s
            small.offer(w);
            if (small.size() > k-1) small.poll();

            // maintain large = k-1 largest  w’s
            large.offer(w);
            if (large.size() > k-1) large.poll();
        }

        long sumMin = 0, sumMax = 0;
        for (long v : small) sumMin += v;
        for (long v : large) sumMax += v;

        return new long[]{ base + sumMin, base + sumMax };
    }

    public static void main(String[] args) {
        int[] cost1 = {10, 20, 30, 40, 50};
        System.out.println(Arrays.toString(minMaxCost(cost1, 3)));
        // → [140, 220]

        int[] cost2 = {5, 4, 2, 1};
        System.out.println(Arrays.toString(minMaxCost(cost2, 2)));
        // → [9, 15]
    }
}
