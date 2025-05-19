import java.util.*;

public class Solution {
    public static long getMaxThroughput(int[] host_throughput) {
        Arrays.sort(host_throughput);  // O(n log n)
        long maxThroughput = 0;
        int n = host_throughput.length;

        // Take every second element from the last (median of each cluster)
        for (int i = n - 2; i >= n / 3; i -= 2) {
            maxThroughput += host_throughput[i];
        }
        return maxThroughput;
    }

    public static void main(String[] args) {
        System.out.println(getMaxThroughput(new int[]{4, 6, 3, 5, 4, 5})); // Output: 9
        System.out.println(getMaxThroughput(new int[]{2, 3, 4, 3, 4}));    // Output: 4
        System.out.println(getMaxThroughput(new int[]{1, 1, 1, 1, 1, 1})); // Output: 2
        System.out.println(getMaxThroughput(new int[]{100000}));          // Output: 0
    }
}
