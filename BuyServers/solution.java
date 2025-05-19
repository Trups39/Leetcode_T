import java.util.*;

public class Solution {
    public int minCostToPurchaseServers(int[] power, int[] price, int target) {
        List<Integer> ones = new ArrayList<>(), twos = new ArrayList<>();
        for (int i = 0; i < power.length; i++) {
            if (price[i] == 1) ones.add(power[i]);
            else twos.add(power[i]);
        }
        // sort descending
        Collections.sort(ones, Collections.reverseOrder());
        Collections.sort(twos, Collections.reverseOrder());

        // prefix sums
        long[] pref1 = new long[ones.size() + 1];
        for (int i = 0; i < ones.size(); i++) {
            pref1[i+1] = pref1[i] + ones.get(i);
        }
        long[] pref2 = new long[twos.size() + 1];
        for (int j = 0; j < twos.size(); j++) {
            pref2[j+1] = pref2[j] + twos.get(j);
        }

        int ans = Integer.MAX_VALUE;
        int j = twos.size();  // start with taking all 2‑credit machines
        for (int i = 0; i <= ones.size(); i++) {
            long sum1 = pref1[i];
            if (sum1 >= target) {
                ans = Math.min(ans, i);
                break;  // further i only increases cost
            }
            long need = target - sum1;
            // decrease j until pref2[j] >= need
            while (j > 0 && pref2[j-1] >= need) {
                j--;
            }
            if (j <= twos.size() && pref2[j] >= need) {
                ans = Math.min(ans, i + 2 * j);
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test Case 1
        int[] power1 = {4, 4, 6, 7};
        int[] price1 = {1, 1, 2, 2};
        int target1 = 7;
        System.out.println(sol.minCostToPurchaseServers(power1, price1, target1));
        // Expected: 2

        // Test Case 2
        int[] power2 = {5, 10, 3};
        int[] price2 = {2, 2, 1};
        int target2 = 12;
        System.out.println(sol.minCostToPurchaseServers(power2, price2, target2));
        // Expected: 3

        // Test Case 3
        int[] power3 = {1, 1, 1};
        int[] price3 = {1, 1, 1};
        int target3 = 5;
        System.out.println(sol.minCostToPurchaseServers(power3, price3, target3));
        // Expected: -1

        // Test Case 4: only price-1 machines enough
        int[] power4 = {2, 3, 4, 5};
        int[] price4 = {1, 1, 1, 1};
        int target4 = 9;
        System.out.println(sol.minCostToPurchaseServers(power4, price4, target4));
        // Expected: 3  (5+4 ≥ 9, cost = 2; but 2+3+4 ≥ 9, cost = 3 → 2-credit machines absent → best is 2 machines cost=2? Actually 5+4=9 cost=2 → should get 2)

        // Test Case 5: only price-2 machines
        int[] power5 = {8, 7, 6};
        int[] price5 = {2, 2, 2};
        int target5 = 13;
        System.out.println(sol.minCostToPurchaseServers(power5, price5, target5));
        // Expected: 4  (8+7 ≥13, cost=4)

        // Test Case 6: mix, exact fit
        int[] power6 = {5, 5, 5, 1};
        int[] price6 = {1, 2, 2, 1};
        int target6 = 11;
        System.out.println(sol.minCostToPurchaseServers(power6, price6, target6));
        // Possible picks: 5(1)+5(2)+5(2)=15 cost=5; or 5(2)+5(2)+1(1)+5(1)=16 cost=6; best is cost=5
        // Expected: 5
    }
}
--------------------------------------------------------------------------------------------------------------------------------------

import java.util.*;

public class Solution {
    /**
     * Returns the minimum total price to get total power ≥ target,
     * or -1 if impossible.
     */
    public static int minCostToPurchaseServers(
        int[] power,
        int[] price,
        int target
    ) {
        int n = power.length;

        // 1) Separate into price‑1 and price‑2 lists
        List<Integer> A = new ArrayList<>();
        List<Integer> B = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (price[i] == 1) A.add(power[i]);
            else             B.add(power[i]);
        }

        // 2) Sort descending
        Collections.sort(A, Collections.reverseOrder());
        Collections.sort(B, Collections.reverseOrder());

        // 3) Build prefix sums (0-based, with pref[0]=0)
        long[] prefA = new long[A.size()+1], prefB = new long[B.size()+1];
        for (int i = 0; i < A.size(); i++) {
            prefA[i+1] = prefA[i] + A.get(i);
        }
        for (int j = 0; j < B.size(); j++) {
            prefB[j+1] = prefB[j] + B.get(j);
        }

        int ans = Integer.MAX_VALUE;
        int maxB = B.size();

        // 4) Try taking j machines from B (cost 2j), 0 ≤ j ≤ maxB
        for (int j = 0; j <= maxB; j++) {
            long powerB = prefB[j];
            long need = target - powerB;
            if (need <= 0) {
                // Enough power with B alone
                ans = Math.min(ans, 2*j);
            } else {
                // Binary search smallest i with prefA[i] ≥ need
                int i = lowerBound(prefA, need);
                if (i != -1) {
                    ans = Math.min(ans, 2*j + i);
                }
            }
        }

        return (ans == Integer.MAX_VALUE ? -1 : ans);
    }

    // Returns smallest index i in pref such that pref[i] ≥ target,
    // or -1 if none.
    private static int lowerBound(long[] pref, long target) {
        int lo = 0, hi = pref.length; // hi = n+1
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (pref[mid] >= target) hi = mid;
            else                     lo = mid + 1;
        }
        return lo < pref.length ? lo : -1;
    }

    // ——— Sample Tests ———
    public static void main(String[] args) {
        test(new int[]{4,4,6,7}, new int[]{1,1,2,2}, 7,   2);
        test(new int[]{5,5,5,5}, new int[]{2,2,2,2}, 7,  -1);
        test(new int[]{10,1,10}, new int[]{1,2,1}, 19,  3);
        test(new int[]{8,2,4,7},  new int[]{1,2,1,2}, 15, 4);
        test(new int[]{1},       new int[]{2},       1,   -1);
    }

    private static void test(int[] p, int[] c, int t, int exp) {
        int ans = minCostToPurchaseServers(p, c, t);
        System.out.printf("power=%s, price=%s, target=%d → %d (exp=%d)%n",
            Arrays.toString(p), Arrays.toString(c), t, ans, exp);
    }
}
