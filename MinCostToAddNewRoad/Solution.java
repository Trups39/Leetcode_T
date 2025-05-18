import java.util.*;

public class Solution {
    public int minCostToAddNewRoads(int N, int[][] connections) {
        // 1) Sort edges by cost
        Arrays.sort(connections, Comparator.comparingInt(a -> a[2]));

        // 2) DSU init
        DSU dsu = new DSU(N + 1); // 1-indexed

        long total = 0;
        int used = 0;

        // 3) Kruskal
        for (int[] edge : connections) {
            int u = edge[0], v = edge[1], w = edge[2];
            if (dsu.find(u) != dsu.find(v)) {
                dsu.union(u, v);
                total += w;
                if (++used == N - 1) break;
            }
        }

        // 4) Check connectivity
        // After MST, must have used N-1 edges and all in one comp
        if (used == N - 1) return (int) total;
        return -1;
    }

    // --- Disjoint‑Set (Union‑Find) ---
    private static class DSU {
        private final int[] parent, rank;
        public DSU(int n) {
            parent = new int[n];
            rank   = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        public int find(int x) {
            return parent[x] == x ? x : (parent[x] = find(parent[x]));
        }
        public void union(int x, int y) {
            x = find(x); y = find(y);
            if (x == y) return;
            if (rank[x] < rank[y]) parent[x] = y;
            else if (rank[y] < rank[x]) parent[y] = x;
            else {
                parent[y] = x;
                rank[x]++;
            }
        }
    }

    // --- Example Tests ---
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Example 1
        int N1 = 3;
        int[][] con1 = {{1,2,5},{1,3,6},{2,3,1}};
        System.out.println(sol.minCostToAddNewRoads(N1, con1));
        // Expected: 6  (edges [2‑3 cost=1] + [1‑2 cost=5])

        // Example 2
        int N2 = 4;
        int[][] con2 = {{1,2,3},{3,4,4}};
        System.out.println(sol.minCostToAddNewRoads(N2, con2));
        // Expected: -1 (cannot connect city 2/3)

        // Additional:
        // Single city
        System.out.println(sol.minCostToAddNewRoads(1, new int[0][3])); 
        // Expected: 0

        // Already fully connected graph
        int N3 = 4;
        int[][] con3 = {
          {1,2,1},{2,3,2},{3,4,3},{1,4,10}
        };
        System.out.println(sol.minCostToAddNewRoads(N3, con3));
        // Expected: 1+2+3=6
    }
}
