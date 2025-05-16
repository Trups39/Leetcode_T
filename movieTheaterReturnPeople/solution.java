import java.util.*;

public class MovieScheduler {
    public static void main(String[] args) {
        Solution sol = new Solution();
        
        // Example test:
        int[] startTime    = {1, 2, 3, 3};
        int[] endTime      = {3, 4, 5, 6};
        int[] peopleCount  = {50, 10, 40, 70};
        // We can choose shows at [1–3] (50) and [3–6] (70) → 120,
        // or [3–5] (40) and [3–6] (70) → 110, etc. Best = 120.
        System.out.println("Max people: " + sol.maxPeople(startTime, endTime, peopleCount));
        // Expected: 120
    }
}

class Solution {
    public int maxPeople(int[] startTime, int[] endTime, int[] peopleCount) {
        int n = startTime.length;
        // Pack shows and sort by end time
        Show[] shows = new Show[n];
        for (int i = 0; i < n; i++) {
            shows[i] = new Show(startTime[i], endTime[i], peopleCount[i]);
        }
        Arrays.sort(shows, Comparator.comparingInt(s -> s.end));
        
        // Extract end times for binary search
        int[] ends = new int[n];
        for (int i = 0; i < n; i++) {
            ends[i] = shows[i].end;
        }
        
        // dp[i] = max people we can seat using the first i shows (1-indexed)
        long[] dp = new long[n + 1];
        dp[0] = 0;
        
        for (int i = 1; i <= n; i++) {
            // Option 1: skip this show
            dp[i] = dp[i - 1];
            
            // Option 2: take this show
            long include = shows[i - 1].count;
            int s = shows[i - 1].start;
            // find rightmost j < i such that ends[j] <= s
            int j = Arrays.binarySearch(ends, 0, i - 1, s);
            if (j < 0) {
                j = -j - 2;  // insertionPoint = -j-1 => last ≤ s is insertionPoint-1
            } else {
                // if multiple shows end exactly at time s, choose the rightmost
                while (j + 1 < i - 1 && ends[j + 1] == s) {
                    j++;
                }
            }
            if (j >= 0) {
                include += dp[j + 1];
            }
            
            dp[i] = Math.max(dp[i], include);
        }
        
        return (int) dp[n];
    }
    
    private static class Show {
        int start, end, count;
        Show(int s, int e, int c) {
            start = s; end = e; count = c;
        }
    }
}
