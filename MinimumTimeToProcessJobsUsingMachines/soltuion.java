import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Minimum makespan on identical parallel machines using LPT heuristic
 */
public class MinProcessingTime {

    public static long minTime(int n, int[] jobs) {
        int m = jobs.length;
        if (n >= m) {
            int max = 0;
            for (int t : jobs) max = Math.max(max, t);
            return max;               // each job on its own machine
        }

        // 1. sort descending
        Integer[] arr = Arrays.stream(jobs).boxed().toArray(Integer[]::new);
        Arrays.sort(arr, Collections.reverseOrder());

        // 2. min-heap of current loads
        PriorityQueue<Long> heap = new PriorityQueue<>(n); // only loads needed
        for (int i = 0; i < n; i++) heap.offer(0L);

        long makespan = 0;
        for (int t : arr) {
            long load = heap.poll();
            load += t;
            makespan = Math.max(makespan, load);
            heap.offer(load);
        }
        return makespan;
    }

    /* ---------- simple runner ---------- */
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        /* for the platform’s custom tests – comment out in the OA editor
        int n = Integer.parseInt(br.readLine().trim());
        String[] parts = br.readLine().trim().split("\\s+");
        int[] jobs = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
        System.out.println(minTime(n, jobs));
        */

        // local demonstrations
        System.out.println(minTime(3, new int[]{5, 2, 6, 1, 4})); // 6
        System.out.println(minTime(2, new int[]{2, 2, 2, 2, 2})); // 6
        System.out.println(minTime(4, new int[]{9, 1, 1, 1}));    // 9
        System.out.println(minTime(1, new int[]{3, 4, 5}));       // 12
        System.out.println(minTime(5, new int[]{7, 7, 7, 7, 7})); // 7
    }
}
