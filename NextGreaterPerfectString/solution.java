public class NextPerfect {
    public static String nextGreaterPerfect(String s) {
        int n = s.length();
        char[] a = s.toCharArray();

        // 1) Precompute which prefixes [0..i] are “perfect” (no equal adjacents)
        boolean[] goodPref = new boolean[n+1];
        goodPref[0] = true;
        for (int i = 1; i < n; i++) {
            goodPref[i] = goodPref[i-1] && (a[i] != a[i-1]);
        }
        goodPref[n] = goodPref[n-1];

        // 2) Try change position i from right→left, but only where prefix [0..i-1] is perfect
        for (int i = n - 1; i >= 0; i--) {
            if (!goodPref[i]) continue;                // skip if prefix has a violation
            char prev = (i == 0 ? 0 : a[i-1]);        // char we must differ from

            // 3) Try the next letter > a[i]
            for (char c = (char)(a[i] + 1); c <= 'z'; c++) {
                if (c == prev) continue;              // must not repeat

                // build result up through i
                char[] t = new char[n];
                System.arraycopy(a, 0, t, 0, i);
                t[i] = c;

                // 4) Fill suffix j=i+1..n-1 with the smallest possible letters
                for (int j = i + 1; j < n; j++) {
                    for (char x = 'a'; x <= 'z'; x++) {
                        if (x != t[j-1]) {
                            t[j] = x;
                            break;
                        }
                    }
                }
                return new String(t);
            }
        }

        // no position worked → no lexicographically larger perfect string
        return "-1";
    }

    // — Simple main to demo the examples —
    public static void main(String[] args) {
        System.out.println(nextGreaterPerfect("abzzzcd"));  // ➜ "acababa"
        System.out.println(nextGreaterPerfect("zzab"));     // ➜ "-1"
    }
}
