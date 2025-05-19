import java.util.*;

public class AmazonLoadBalancerSegmentTree {

    static class SegmentTree {
        int size;
        int[] minLoad;

        public SegmentTree(int n) {
            size = n;
            minLoad = new int[4 * n];
            build(1, 0, size - 1);
        }

        private void build(int node, int l, int r) {
            if (l == r) {
                minLoad[node] = 0;
            } else {
                int mid = (l + r) / 2;
                build(2 * node, l, mid);
                build(2 * node + 1, mid + 1, r);
                minLoad[node] = 0;
            }
        }

        public void update(int idx, int node, int l, int r) {
            if (l == r) {
                minLoad[node]++;
            } else {
                int mid = (l + r) / 2;
                if (idx <= mid) update(idx, 2 * node, l, mid);
                else update(idx, 2 * node + 1, mid + 1, r);
                minLoad[node] = Math.min(minLoad[2 * node], minLoad[2 * node + 1]);
            }
        }

        public int query(int node, int l, int r, int ql, int qr, int[] loads) {
            if (qr < l || r < ql) return -1;
            if (ql <= l && r <= qr) {
                if (l == r) return l;
            }
            int mid = (l + r) / 2;
            int left = query(2 * node, l, mid, ql, qr, loads);
            int right = query(2 * node + 1, mid + 1, r, ql, qr, loads);

            if (left == -1) return right;
            if (right == -1) return left;
            return loads[left] <= loads[right] ? left : right;
        }
    }

    public static int[] findRequestTarget(int numServers, int[] requests) {
        int[] loads = new int[numServers];
        int[] result = new int[requests.length];
        SegmentTree tree = new SegmentTree(numServers);

        for (int i = 0; i < requests.length; i++) {
            int h = requests[i] % numServers;
            int minIdx = -1;

            // Check circular range [h, numServers-1] + [0, h-1] split in two queries
            if (h <= numServers - 1) {
                minIdx = tree.query(1, 0, numServers - 1, h, numServers - 1, loads);
            }
            if (h > 0) {
                int altIdx = tree.query(1, 0, numServers - 1, 0, h - 1, loads);
                if (minIdx == -1 || (altIdx != -1 && loads[altIdx] < loads[minIdx]) ||
                    (altIdx != -1 && loads[altIdx] == loads[minIdx] && altIdx < minIdx)) {
                    minIdx = altIdx;
                }
            }

            result[i] = minIdx;
            loads[minIdx]++;
            tree.update(minIdx, 1, 0, numServers - 1);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] res1 = findRequestTarget(5, new int[]{3, 2, 3, 2, 4});
        System.out.println("Test Case 1 Output: " + Arrays.toString(res1)); // Expected: [0, 1, 2, 3, 4]

        int[] res2 = findRequestTarget(5, new int[]{4, 0, 2, 2});
        System.out.println("Test Case 2 Output: " + Arrays.toString(res2)); // Expected: [0, 1, 2, 3]

        int[] res3 = findRequestTarget(5, new int[]{0, 1, 2, 3});
        System.out.println("Test Case 3 Output: " + Arrays.toString(res3)); // Expected: [0, 1, 2, 3]
    }
}
