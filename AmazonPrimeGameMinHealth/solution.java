public class AmazonArmorHealth {

    public static long minimumHealth(int[] power, int armor) {
        long totalDamage = 0;
        int maxPower = 0;

        for (int p : power) {
            totalDamage += p;
            maxPower = Math.max(maxPower, p);
        }

        return totalDamage - Math.min(armor, maxPower) + 1;
    }

    public static void main(String[] args) {
        // Example 1
        int[] power1 = {1, 2, 6, 7};
        int armor1 = 5;
        System.out.println(minimumHealth(power1, armor1));  // Output: 12

        // Example 2
        int[] power2 = {10, 20, 30};
        int armor2 = 25;
        System.out.println(minimumHealth(power2, armor2));  // Output: 36

        // Example 3
        int[] power3 = {100000, 200000, 300000, 400000};
        int armor3 = 100000;
        System.out.println(minimumHealth(power3, armor3));  // Output: 900001
    }
}
