import java.util.*;

public class AlexaAnalyzer {
    public static int[] countQuietSkills(
            int numSkills,
            int[][] requestLogs,    // unsorted [skillId, timestamp]
            int timeWindow,
            int[] queryTimes        // unsorted queries
    ) {
        int m = requestLogs.length, q = queryTimes.length;

        // 1) SORT ONCE: logs by timestamp
        Arrays.sort(requestLogs, Comparator.comparingInt(a -> a[1]));

        // 2) SORT ONCE: queries by time, but keep original indices
        int[][] queries = new int[q][2];
        for (int i = 0; i < q; i++) {
            queries[i][0] = queryTimes[i];
            queries[i][1] = i;
        }
        Arrays.sort(queries, Comparator.comparingInt(a -> a[0]));

        // 3) Sliding‐window over logs, maintain freq[] + count of zeros
        int[] freq = new int[numSkills+1];
        int zeroCount = numSkills;
        int l = 0, r = 0;
        int[] answer = new int[q];

        for (int[] qu : queries) {
            int T = qu[0], qi = qu[1], start = T - timeWindow;

            // advance r to include all logs ≤ T
            while (r < m && requestLogs[r][1] <= T) {
                int sid = requestLogs[r][0];
                if (freq[sid]++ == 0) zeroCount--;
                r++;
            }
            // advance l to exclude logs < start
            while (l < m && requestLogs[l][1] < start) {
                int sid = requestLogs[l][0];
                if (--freq[sid] == 0) zeroCount++;
                l++;
            }

            answer[qi] = zeroCount;
        }

        return answer;
    }

    public static void main(String[] args) {
        int numSkills = 5;
        int[][] logs = {
            {1,10}, {2,15}, {3,20}, {2,30}, {5,35}
        };
        int timeWindow = 10;
        int[] queryTimes = {20,25,40};

        int[] res = countQuietSkills(numSkills, logs, timeWindow, queryTimes);
        System.out.println(Arrays.toString(res));  // [2, 3, 3]
    }
}
