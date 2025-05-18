import java.util.*;

public class CinemaShows {
    static class Show {
        final long start, end;
        final int vol;
        Show(long s, long e, int v) {
            this.start = s;
            this.end   = e;
            this.vol   = v;
        }
    }

    public static int cinemaShows(int[] start, int[] duration, int[] volume) {
        int n = start.length;
        Show[] shows = new Show[n];
        for (int i = 0; i < n; i++) {
            shows[i] = new Show(start[i], (long)start[i] + duration[i], volume[i]);
        }
        Arrays.sort(shows, Comparator.comparingLong(s -> s.end));

        long[] ends = new long[n];
        for (int i = 0; i < n; i++) ends[i] = shows[i].end;

        long[] dp = new long[n + 1];
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            // skip
            long best = dp[i-1];
            // take
            Show cur = shows[i-1];
            int j = Arrays.binarySearch(ends, cur.start);
            if (j < 0) j = -j - 2;
            long take = cur.vol + (j >= 0 ? dp[j+1] : 0);
            dp[i] = Math.max(best, take);
        }

        return (int) dp[n];
    }

    public static void main(String[] args) {
        System.out.println(cinemaShows(
          new int[]{10,5,15,18,30},
          new int[]{20,12,20,35,35},
          new int[]{50,51,20,25,10})
        ); // 76

        System.out.println(cinemaShows(
          new int[]{1,2,4},
          new int[]{2,2,1},
          new int[]{1,2,3})
        ); // 5

        System.out.println(cinemaShows(
          new int[]{1,5,10},
          new int[]{2,2,2},
          new int[]{10,20,30})
        ); // 60

        System.out.println(cinemaShows(
          new int[]{0,0,0},
          new int[]{10,10,10},
          new int[]{5,15,10})
        ); // 15

        System.out.println(cinemaShows(
          new int[]{0,3,6},
          new int[]{3,3,3},
          new int[]{10,10,50})
        ); // 70

        System.out.println(cinemaShows(
          new int[0],
          new int[0],
          new int[0])
        ); // 0
    }
}
