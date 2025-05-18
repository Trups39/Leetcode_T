import java.util.*;

public class Main {
    public static int beautifulCanvas(int n, int m, int k, int[][] paint) {
        int N = n * m;
        int lo = 1, hi = N, ans = -1;
        // binary search on time t
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (canBeBeautiful(n, m, k, paint, mid)) {
                ans = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return ans;
    }

    // check if first 't' paints make some k×k fully painted
    private static boolean canBeBeautiful(int n, int m, int k,
                                          int[][] paint, int t) {
        // 1) build grid
        //    use 1-based indexing for easy prefix sums
        int[][] grid = new int[n+1][m+1];
        for (int i = 0; i < t; i++) {
            int r = paint[i][0], c = paint[i][1];
            grid[r][c] = 1;
        }
        // 2) build 2D prefix sums
        int[][] pref = new int[n+1][m+1];
        for (int i = 1; i <= n; i++) {
            int rowSum = 0;
            for (int j = 1; j <= m; j++) {
                rowSum += grid[i][j];
                pref[i][j] = pref[i-1][j] + rowSum;
            }
        }
        int need = k * k;
        // 3) scan all k×k squares
        for (int i = 1; i + k - 1 <= n; i++) {
            for (int j = 1; j + k - 1 <= m; j++) {
                int r2 = i + k - 1, c2 = j + k - 1;
                int total = pref[r2][c2]
                          - pref[i-1][c2]
                          - pref[r2][j-1]
                          + pref[i-1][j-1];
                if (total == need) return true;
            }
        }
        return false;
    }

    // --- Sample Test from Prompt ---
    public static void main(String[] args) {
        int n = 2, m = 3, k = 2;
        int[][] paint = {
            {1,2}, {2,3}, {2,1},
            {1,3}, {2,2}, {1,1}
        };
        System.out.println(beautifulCanvas(n,m,k,paint));
        // expected 5
    }
}
