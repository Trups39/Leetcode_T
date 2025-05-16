import java.util.*;

public class Main {
    public static void main(String[] args) {
        Main solution = new Main();

        int[] earnedPoints1 = {1, 3, 4};
        System.out.println("Output: " + solution.allAboutRewardsOptimized(earnedPoints1)); // Expected: 2

        int[] earnedPoints2 = {8,10,9};
        System.out.println("Output: " + solution.allAboutRewardsOptimized(earnedPoints2)); // Expected: 2
        
        int[] earnedPoints3 = {5, 7, 9, 11};
        System.out.println("Output: " + solution.allAboutRewardsOptimized(earnedPoints3)); // Expected: 1
    }

    public int allAboutRewardsOptimized(int[] earnedPoints) {
        int n = earnedPoints.length;

        // Pair points with their original index
        int[][] pointsWithIndex = new int[n][2];
        for (int i = 0; i < n; i++) {
            pointsWithIndex[i][0] = earnedPoints[i];
            pointsWithIndex[i][1] = i;
        }

        // Sort descending by initial points
        Arrays.sort(pointsWithIndex, (a, b) -> b[0] - a[0]);

        // Compute what each non‐winner would get
        // Highest initial gets n−1, next n−2, …, lowest gets 0
        int[] tournamentPoints = new int[n];
        for (int rank = 0; rank < n; rank++) {
            int idx = pointsWithIndex[rank][1];
            tournamentPoints[idx] = (n - rank - 1);
        }

        // Find the maximum total someone can achieve WITHOUT winning
        int maxOtherTotal = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            maxOtherTotal = Math.max(maxOtherTotal, earnedPoints[i] + tournamentPoints[i]);
        }

        // Count how many, if they win (+n), can reach or exceed that
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (earnedPoints[i] + n >= maxOtherTotal) {
                count++;
            }
        }

        return count;
    }
}
