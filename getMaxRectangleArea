import java.util.*;

public class MaxRectangleArea {
    static final int MOD = 1_000_000_007;

    public static long maxTotalArea(int[] sideLengths) {
        Arrays.sort(sideLengths);
        int n = sideLengths.length;
        List<Integer> sides = new ArrayList<>();
        for (int i = n - 1; i > 0; ) {
            if (sideLengths[i] - sideLengths[i - 1] <= 1) {
                sides.add(sideLengths[i - 1]);
                i -= 2;
            } else {
                i--;
            }
        }
        long total = 0;
        for (int j = 0; j + 1 < sides.size(); j += 2) {
            long area = 1L * sides.get(j) * sides.get(j + 1) % MOD;
            total = (total + area) % MOD;
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(maxTotalArea(new int[]{2, 3, 3, 4, 6, 6, 8, 8})); // 54
        System.out.println(maxTotalArea(new int[]{2, 1, 6, 5, 4, 4}));       // 20
        System.out.println(maxTotalArea(new int[]{2, 6, 6, 2, 3, 5}));       // 12
        System.out.println(maxTotalArea(new int[]{4, 4, 4, 4}));             // 16
        System.out.println(maxTotalArea(new int[]{1, 2, 3, 4}));             // 0
    }
}
