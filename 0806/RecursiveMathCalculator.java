public class RecursiveMathCalculator {

    // 1. 組合數 C(n, k) = C(n-1, k-1) + C(n-1, k)
    public static int combination(int n, int k) {
        if (k == 0 || k == n) {
            return 1;
        }
        return combination(n - 1, k - 1) + combination(n - 1, k);
    }

    // 2. 卡塔蘭數 Catalan(n) = Σ C(i) * C(n-1-i), i=0~n-1
    public static long catalan(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        long result = 0;
        for (int i = 0; i < n; i++) {
            result += catalan(i) * catalan(n - 1 - i);
        }
        return result;
    }

    // 3. 漢諾塔步數 Hanoi(n) = 2 * Hanoi(n-1) + 1
    public static long hanoiSteps(int n) {
        if (n == 1) {
            return 1;
        }
        return 2 * hanoiSteps(n - 1) + 1;
    }

    // 4. 判斷是否為回文數（遞迴）
    public static boolean isPalindrome(int number) {
        String str = String.valueOf(number);
        return isPalindromeHelper(str, 0, str.length() - 1);
    }

    private static boolean isPalindromeHelper(String str, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }
        return isPalindromeHelper(str, left + 1, right - 1);
    }

    // 主程式：測試各功能
    public static void main(String[] args) {
        // 測試組合數
        System.out.println("C(5, 2) = " + combination(5, 2));

        // 測試卡塔蘭數
        System.out.println("Catalan(5) = " + catalan(5));

        // 測試漢諾塔
        System.out.println("Hanoi steps for 4 disks = " + hanoiSteps(4));

        // 測試回文數
        int num1 = 12321;
        int num2 = 12345;
        System.out.println(num1 + " 是否為回文數？" + isPalindrome(num1));
        System.out.println(num2 + " 是否為回文數？" + isPalindrome(num2));
    }
}