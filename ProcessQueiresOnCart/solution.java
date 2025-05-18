import java.util.*;

public class Solution {
    public List<Integer> processQueriesOnCart(int[] items, int[] query) {
        int n = items.length, q = query.length;
        int total = n + q;

        // Doubly‑linked list in arrays 0..total
        int[] nxt = new int[total + 1], prv = new int[total + 1], val = new int[total + 1];
        // Sentinel setup: 0 is head,  total is tail
        nxt[0] = total; 
        prv[total] = 0;

        // Map itemID → deque of node‑indices still in cart
        Map<Integer, Deque<Integer>> map = new HashMap<>();

        // 1) Initialize with initial items at nodes 1..n
        for (int i = 1; i <= n; i++) {
            int id = items[i-1];
            val[i] = id;
            // insert node i before tail (index total)
            nxt[prv[total]] = i;
            prv[i] = prv[total];
            nxt[i] = total;
            prv[total] = i;
            // track position
            map.computeIfAbsent(id, k -> new ArrayDeque<>()).addLast(i);
        }

        int cur = n;  // last used node index

        // 2) Process queries
        for (int x : query) {
            if (x > 0) {
                // append new node
                cur++;
                val[cur] = x;
                nxt[prv[total]] = cur;
                prv[cur] = prv[total];
                nxt[cur] = total;
                prv[total] = cur;
                map.computeIfAbsent(x, k -> new ArrayDeque<>()).addLast(cur);
            } else {
                int id = -x;
                Deque<Integer> dq = map.get(id);
                int nodeIdx = dq.pollFirst();       // first occurrence
                // unlink nodeIdx
                nxt[prv[nodeIdx]] = nxt[nodeIdx];
                prv[nxt[nodeIdx]] = prv[nodeIdx];
                // (we leave val[nodeIdx] as is; it's out of list now)
            }
        }

        // 3) Collect result
        List<Integer> result = new ArrayList<>(n + q);
        for (int i = nxt[0]; i != total; i = nxt[i]) {
            result.add(val[i]);
        }
        return result;
    }

    // --- Example tests ---
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Example 1
        int[] items1 = {1,2,1,2,1}, q1 = {-1,-1,3,4,-3};
        System.out.println(sol.processQueriesOnCart(items1, q1));
        // → [2, 2, 1, 4]

        // Example 2
        int[] items2 = {5,1,2,2,4,6}, q2 = {1,-2,-1,-1};
        System.out.println(sol.processQueriesOnCart(items2, q2));
        // → [5, 2, 4, 6]
    }
}
