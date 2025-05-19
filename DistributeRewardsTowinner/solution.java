import java.util.*;

public class DistributeRewards {

    private static class Node {
        int score;
        int idx;
        Node(int s, int i) { score = s; idx = i; }
    }

    public static int[] findTopTwo(int[] rewards, int kBoost) {
        int n = rewards.length;

        /* ---------- step 1: select k winners ---------- */
        PriorityQueue<Node> heap = new PriorityQueue<>(kBoost + 1,
            (a, b) -> {                 // worst on top
                if (a.score != b.score) return Integer.compare(a.score, b.score);
                return Integer.compare(b.idx, a.idx); // larger index = worse on tie
            });

        for (int i = 0; i < n; i++) {
            heap.offer(new Node(rewards[i], i));
            if (heap.size() > kBoost) heap.poll();
        }

        /* ---------- step 2: boost ---------- */
        while (!heap.isEmpty()) {
            Node node = heap.poll();
            rewards[node.idx] += kBoost;
        }

        /* ---------- step 3: find the global best two ---------- */
        int best1Idx = -1, best2Idx = -1;
        int best1Val = -1, best2Val = -1;

        for (int i = 0; i < n; i++) {
            int val = rewards[i];

            if (val > best1Val || (val == best1Val && i < best1Idx)) {
                // shift current best1 to best2
                best2Val = best1Val; best2Idx = best1Idx;
                best1Val = val;      best1Idx = i;
            } else if (val > best2Val || (val == best2Val && i < best2Idx)) {
                best2Val = val; best2Idx = i;
            }
        }
        return new int[]{best1Idx, best2Idx};
    }

    /* ---------------- driver ---------------- */
    public static void main(String[] args) {
        runTest(new int[]{3, 8, 10, 9}, 2);        // expected 2 3
        runTest(new int[]{5, 5, 5}, 1);            // expected 0 1
        runTest(new int[]{1, 2}, 1);               // expected 1 0
        runTest(new int[]{7, 4, 9, 9, 8}, 3);      // check tie handling
    }

    private static void runTest(int[] arr, int k) {
        int[] copy = Arrays.copyOf(arr, arr.length);
        int[] ans = findTopTwo(copy, k);
        System.out.printf("Output: %d %d%n", ans[0], ans[1]);
    }
}
