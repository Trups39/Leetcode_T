import java.util.*;


public class Solution {
    /**
     * Schedules servers in descending order of w/p (Smith’s rule) to
     * minimize sum of w_i * completionTime_i, then adds 1 final request.
     */
     static class Server {
        long w, p;
        Server(long w, long p) { this.w = w; this.p = p; }
    }
    
    public long getMinRequests(int[] request, int[] health, int k) {
        int n = request.length;
        List<Server> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            long hits = (health[i] + k - 1L) / k;  // ceil division
            list.add(new Server(request[i], hits));
        }
        // sort by descending ratio w/p => compare b.w*a.p vs a.w*b.p
        list.sort((a, b) -> Long.compare(b.w * a.p, a.w * b.p));

        long time = 0, total = 0;
        for (Server s : list) {
            time  += s.p;           // finish time of this server
            total += s.w * time;    // accumulate weighted completion
        }
        return total + 1;  // +1 for the final request
    }

    // ——— Test Harness ———
    public static void main(String[] args) {
        Solution sol = new Solution();

        run(new int[]{3, 4}, new int[]{4, 6}, 3, 21, sol);
        run(new int[]{5},       new int[]{10},   7, 11, sol);
        run(new int[]{1, 2, 3}, new int[]{5,5,5}, 1, 51, sol);
        run(new int[]{2, 9, 5, 7, 3},
            new int[]{10,9,8,7,6}, 5,
            // hits = [2,2,2,2,2], ratios = [2/2,9/2,5/2,7/2,3/2] => order 9,7,5,3,2
            // times: 2,4,6,8,10; weighted sum=9*2+7*4+5*6+3*8+2*10 = 18+28+30+24+20=120; +1 => 121
            121, sol);
        run(new int[]{10, 1, 10},
            new int[]{15,5,20}, 5,
            // hits=[3,1,4], ratios=[10/3≈3.33,1/1=1,10/4=2.5] => order i=0,i=2,i=1
            // times:3,7,8; sum=10*3+10*7+1*8=30+70+8=108; +1=>109
            109, sol);
    }

    private static void run(int[] req, int[] hp, int k, long expected, Solution sol) {
        long got = sol.getMinRequests(req, hp, k);
        System.out.printf(
            "req=%s, health=%s, k=%d → %d (exp=%d)%n",
            Arrays.toString(req),
            Arrays.toString(hp),
            k, got, expected
        );
    }
}
