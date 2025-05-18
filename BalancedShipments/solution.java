    public static int maxNumberOfBalancedShipments(int[] weights) {
        int count = 0;
        long segMax = 0;               // current segment's maximum
        for (int w : weights) {
            segMax = Math.max(segMax, w);
            if (segMax > w) {
                // We can end a balanced shipment here.
                count++;
                segMax = 0;           // reset for the next segment
            }
        }
        return count;
    }

--------------------------------------------------------------------------------------------------------------
public static int maxBalancedWithStack(int[] W) {
    Deque<Long> st = new ArrayDeque<>();
    int count = 0;
    for (long w : W) {
        // Remove any smaller‐or‐equal values:
        while (!st.isEmpty() && st.peek() <= w) {
            st.pop();
        }
        // If there is still something bigger above,
        // we have segMax > w → we can cut here.
        if (!st.isEmpty()) {
            count++;
            st.clear();
        }
        // Start next segment with w
        st.push(w);
    }
    return count;
}

