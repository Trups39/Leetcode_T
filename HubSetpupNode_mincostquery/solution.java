import java.util.*;

public class ShippingCentersSorted {
    static long[] prefix;
    static int[] values;
    static int n;

    public static void init(int[] nodes) {
        n = nodes.length;
        values = new int[n+1];
        prefix = new long[n+1];
        for (int i = 1; i <= n; i++) {
            values[i]   = nodes[i-1];
            prefix[i] = prefix[i-1] + values[i];
        }
    }

    public static long queryWithSort(List<Integer> extraHubs) {
        extraHubs.add(n);
        Collections.sort(extraHubs);

        long cost = 0;
        int prev = 1;
        for (int hub : extraHubs) {
            int len = hub - prev + 1;
            long sumSeg = prefix[hub] - prefix[prev-1];
            cost += 1L * len * values[hub] - sumSeg;
            prev = hub + 1;
        }
        return cost;
    }

    public static void main(String[] args) {
        int[] nodes = {10,20,30,40,50};
        init(nodes);

        System.out.println(queryWithSort(new ArrayList<>(Arrays.asList(2,4)))); // 20
        System.out.println(queryWithSort(new ArrayList<>(Arrays.asList(1,3)))); // 20
    }
}
-------------------------------------------------------------------------------------------------------------------------
//treeset
import java.util.*;

public class ShippingCentersTreeSet {
    static long[] prefix;
    static int[] values;
    static int n;

    public static void init(int[] nodes) {
        n = nodes.length;
        values = new int[n+1];
        prefix = new long[n+1];
        for (int i = 1; i <= n; i++) {
            values[i]   = nodes[i-1];
            prefix[i] = prefix[i-1] + values[i];
        }
    }

    public static long queryWithTreeSet(Collection<Integer> extraHubs) {
        TreeSet<Integer> hubs = new TreeSet<>(extraHubs);
        hubs.add(n);

        long cost = 0;
        int prev = 1;
        for (int hub : hubs) {
            int len = hub - prev + 1;
            long sumSeg = prefix[hub] - prefix[prev-1];
            cost += 1L * len * values[hub] - sumSeg;
            prev = hub + 1;
        }
        return cost;
    }

    public static void main(String[] args) {
        int[] nodes = {10,20,30,40,50};
        init(nodes);

        System.out.println(queryWithTreeSet(Arrays.asList(2,4))); // 20
        System.out.println(queryWithTreeSet(Arrays.asList(1,3))); // 20
    }
}
