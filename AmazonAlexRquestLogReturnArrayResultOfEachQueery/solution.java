import java.util.*;

public class StaleSkillCounter {
    static class Query {
        int time, idx;
        Query(int t, int i) { time = t; idx = i; }
    }

    static class Log {
        int skill, time;
        Log(int s, int t) { skill = s; time = t; }
    }

    public static int[] getStaleSkillCount(
        int numSkills,
        int[][] requestLogs,
        int[] queryTimes,
        int timeWindow
    ) {
        int m = requestLogs.length;
        int q = queryTimes.length;

        // 1) Build and sort logs by timestamp
        Log[] logs = new Log[m];
        for (int i = 0; i < m; i++) {
            logs[i] = new Log(requestLogs[i][0], requestLogs[i][1]);
        }
        Arrays.sort(logs, Comparator.comparingInt(l -> l.time));

        // 2) Build and sort queries by queryTime
        Query[] queries = new Query[q];
        for (int i = 0; i < q; i++) {
            queries[i] = new Query(queryTimes[i], i);
        }
        Arrays.sort(queries, Comparator.comparingInt(x -> x.time));

        // 3) Prepare counters
        int[] count = new int[numSkills + 1]; // 1-based skill IDs
        int unique = 0;
        int[] answer = new int[q];

        // 4) Two‐pointer sweep
        int L = 0, R = 0;
        for (Query qu : queries) {
            int T = qu.time;
            int leftBound  = T - timeWindow; // strictly > leftBound
            int rightBound = T;              // ≤ rightBound

            // Advance R to include logs with time ≤ rightBound
            while (R < m && logs[R].time <= rightBound) {
                int s = logs[R].skill;
                if (count[s]++ == 0) unique++;
                R++;
            }

           while (L < m && logs[L].time < leftBound) {
        int s = logs[L].skill;
        if (--count[s] == 0) unique--;
        L++;
    }

            // Now [L..R-1] covers exactly (T-timeWindow, T]
            answer[qu.idx] = numSkills - unique;
        }

        return answer;
    }

    // ——— Quick Test ———
    public static void main(String[] args) {
        int numSkills = 3;
        int[][] logs = { {1,3}, {2,6}, {1,5} };
        int timeWindow = 5;
        int[] queries = {10, 11};

        // Expect [1,2]
        System.out.println(Arrays.toString(
            getStaleSkillCount(numSkills, logs, queries, timeWindow)));

        // Another example from screenshot:
        numSkills = 6;
        timeWindow = 1;
        logs = new int[][] { {3,2},{4,3},{2,6},{6,3} };
        queries = new int[]{1,2,3,4,5,6};
        // Expect [6,5,3,4,6,5]
        System.out.println(Arrays.toString(
            getStaleSkillCount(numSkills, logs, queries, timeWindow)));
    }
}
