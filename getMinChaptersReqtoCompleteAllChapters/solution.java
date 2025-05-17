import java.util.*;

public class ReaderSchedulePQ {
    public static long minDaysPQ(int[] chapters, int k, int p) {
        int n = chapters.length;
        // need[i] = how many days chapter i needs by itself
        int[] need = new int[n];
        for (int i = 0; i < n; i++) {
            need[i] = (chapters[i] + p - 1) / p;
        }

        // min‐heap of end‐indices of days we’ve scheduled
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        long days = 0;

        for (int i = 0; i < n; i++) {
            // 1) Evict any past intervals that no longer cover chapter i
            while (!pq.isEmpty() && pq.peek() < i) {
                pq.poll();
            }
            // 2) pq.size() = how many days already cover chapter i
            int covered = pq.size();
            // 3) If uncovered < need[i], schedule more days starting at i
            while (covered < need[i]) {
                int end = i + k - 1;
                if (end >= n) end = n - 1;
                pq.offer(end);
                days++;
                covered++;
            }
        }

        return days;
    }

    public static void main(String[] args) {
        System.out.println(minDaysPQ(new int[]{10,20,30,40}, 2, 10));  // 6
        System.out.println(minDaysPQ(new int[]{5,1,4,3,2,7,1}, 3, 2)); // 7
    }
}
---------------------------------------------------------------------------------------------------------------------------------------
  public static long minDays(int[] chapters, int k, int p) {
    int n = chapters.length;
    long days = 0;
    int[] need = new int[n];
    for (int i = 0; i < n; i++) 
        need[i] = (chapters[i] + p - 1) / p;

    long[] diff = new long[n+1];
    long sub = 0;
    for (int i = 0; i < n; i++) {
        sub += diff[i];
        long rem = need[i] - sub;
        if (rem > 0) {
            days += rem;
            sub   += rem;
            if (i + k <= n) diff[i+k] -= rem;
        }
    }
    return days;
}
