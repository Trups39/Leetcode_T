import java.io.*;
import java.util.*;

public class Solution {

    public static int getMaxConsecutiveON(String server_states, int k) {
        int n = server_states.length();
        int L = 0, maxLen = 0, zeroGroups = 0;

        for (int R = 0; R < n; R++) {
            char c = server_states.charAt(R);

            // did a new zero-group start at R?
            if (c == '0' && (R == 0 || server_states.charAt(R - 1) == '1')) {
                ++zeroGroups;
            }

            // keep ≤ k zero-groups inside window
            while (zeroGroups > k) {
                // advance L past leading ones
                while (L < n && server_states.charAt(L) == '1') {
                    ++L;
                }
                // advance L past the entire next zero-group
                while (L < n && server_states.charAt(L) == '0') {
                    ++L;
                }
                --zeroGroups;              // we have removed one group
            }

            maxLen = Math.max(maxLen, R - L + 1);
        }
        return maxLen;
    }

    /* ---------- simple driver ---------- */
    public static void main(String[] args) {
        System.out.println(getMaxConsecutiveON("1001", 2));     // 4
        System.out.println(getMaxConsecutiveON("00010", 1));    // 4
        System.out.println(getMaxConsecutiveON("1111", 1));     // 4
        System.out.println(getMaxConsecutiveON("0010010", 2));  // 5
        System.out.println(getMaxConsecutiveON("0", 1));        // 1
        System.out.println(getMaxConsecutiveON("0", 0));        // 0
    }
}
