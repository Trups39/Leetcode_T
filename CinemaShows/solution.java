import java.util.*;

public class CinemaShows {
    static class Show implements Comparable<Show> {
        int start, end, vol;
        Show(int s, int d, int v) {
            start = s;
            end   = s + d;
            vol   = v;
        }
        public int compareTo(Show o) {
            return Integer.compare(this.end, o.end);
        }
    }

    public static int cinemaShows(int[] start, int[] duration, int[] volume) {
        int n = start.length;
        Show[] shows = new Show[n];
        for (int i = 0; i < n; i++) {
            shows[i] = new Show(start[i], duration[i], volume[i]);
        }
        Arrays.sort(shows);

        // Extract ends into a separate array for binary searching
        int[] ends = new int[n];
        for (int i = 0; i < n; i++) ends[i] = shows[i].end;

        // dp[i] = best we can do using shows[0..i]
        long[] dp = new long[n];
        dp[0] = shows[0].vol;

        for (int i = 1; i < n; i++) {
            // 1) Option A: skip show i
            long best = dp[i-1];

            // 2) Option B: take show i
            //    find last j < i with shows[j].end < shows[i].start
            int k = lowerBound(ends, 0, i, shows[i].start);
            int j = k - 1;  
            long take = shows[i].vol + (j >= 0 ? dp[j] : 0L);

            dp[i] = Math.max(best, take);
        }
        return (int) dp[n-1];
    }

    /** Finds the first index in [lo..hi) at which array[idx] >= key. */
    private static int lowerBound(int[] array, int lo, int hi, int key) {
        while (lo < hi) {
            int mid = lo + ((hi - lo) >>> 1);
            if (array[mid] < key) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    // ——— Quick Test ———
    public static void main(String[] args) {
        run(new int[]{10,5,15,18,30}, new int[]{20,12,20,35,35}, new int[]{50,51,20,25,10}, 76);
        run(new int[]{1,2,4},       new int[]{2, 2, 1},       new int[]{1, 2, 3},      4);
        run(new int[]{1},           new int[]{100},          new int[]{999},           999);
        run(new int[]{1,2},         new int[]{10, 5},        new int[]{10,20},         20);
    }

    private static void run(int[] s, int[] d, int[] v, int exp) {
        int ans = cinemaShows(s, d, v);
        System.out.parintf(
          "start=%s dur=%s vol=%s → %d  (exp %d)%n",
          Arrays.toString(s),
          Arrays.toString(d),
          Arrays.toString(v),
          ans, exp
        );
    }
}
