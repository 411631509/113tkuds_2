public class RecursionVsIteration {

    // -------------------
    // 1. 二項式係數 C(n, k)
    // -------------------

    // 遞迴版
    public static int binomialRecursive(int n, int k) {
        if (k == 0 || k == n) return 1;
        return binomialRecursive(n - 1, k - 1) + binomialRecursive(n - 1, k);
    }

    // 迭代版（動態規劃）
    public static int binomialIterative(int n, int k) {
        int[][] dp = new int[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= Math.min(i, k); j++) {
                if (j == 0 || j == i) dp[i][j] = 1;
                else dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
            }
        }

        return dp[n][k];
    }

    // -------------------------------
    // 2. 陣列中所有元素的乘積
    // -------------------------------

    // 遞迴版
    public static int productRecursive(int[] arr, int index) {
        if (index == arr.length) return 1;
        return arr[index] * productRecursive(arr, index + 1);
    }

    // 迭代版
    public static int productIterative(int[] arr) {
        int result = 1;
        for (int value : arr) {
            result *= value;
        }
        return result;
    }

    // -------------------------------
    // 3. 計算字串中元音字母的數量
    // -------------------------------

    // 遞迴版
    public static int countVowelsRecursive(String str, int index) {
        if (index == str.length()) return 0;
        char c = Character.toLowerCase(str.charAt(index));
        int count = (isVowel(c)) ? 1 : 0;
        return count + countVowelsRecursive(str, index + 1);
    }

    // 迭代版
    public static int countVowelsIterative(String str) {
        int count = 0;
        for (char c : str.toLowerCase().toCharArray()) {
            if (isVowel(c)) count++;
        }
        return count;
    }

    private static boolean isVowel(char c) {
        return "aeiou".indexOf(c) != -1;
    }

    // -------------------------------
    // 4. 括號是否配對正確
    // -------------------------------

    // 遞迴版（處理 () [] {}）
    public static boolean isBalancedRecursive(String str) {
        return checkBalanced(str, 0, 0);
    }

    private static boolean checkBalanced(String str, int index, int openCount) {
        if (openCount < 0) return false;
        if (index == str.length()) return openCount == 0;

        char c = str.charAt(index);
        if (c == '(') return checkBalanced(str, index + 1, openCount + 1);
        else if (c == ')') return checkBalanced(str, index + 1, openCount - 1);
        else return checkBalanced(str, index + 1, openCount); // skip other chars
    }

    // 迭代版
    public static boolean isBalancedIterative(String str) {
        int balance = 0;
        for (char c : str.toCharArray()) {
            if (c == '(') balance++;
            else if (c == ')') balance--;
            if (balance < 0) return false;
        }
        return balance == 0;
    }

    // -------------------------------
    // 主程式：測試與效能比較
    // -------------------------------

    public static void main(String[] args) {
        int n = 20, k = 10;
        int[] array = {1, 2, 3, 4, 5};
        String testStr = "Recursion is powerful and elegant!";
        String parens = "(((()(()))))";

        System.out.println("=== 二項式係數 C(" + n + ", " + k + ") ===");
        long t1 = System.nanoTime();
        System.out.println("遞迴結果: " + binomialRecursive(n, k));
        long t2 = System.nanoTime();
        System.out.println("→ 遞迴時間: " + (t2 - t1) + " ns");

        t1 = System.nanoTime();
        System.out.println("迭代結果: " + binomialIterative(n, k));
        t2 = System.nanoTime();
        System.out.println("→ 迭代時間: " + (t2 - t1) + " ns\n");

        System.out.println("=== 陣列乘積 ===");
        System.out.println("遞迴結果: " + productRecursive(array, 0));
        System.out.println("迭代結果: " + productIterative(array) + "\n");

        System.out.println("=== 字串元音數 ===");
        System.out.println("遞迴結果: " + countVowelsRecursive(testStr, 0));
        System.out.println("迭代結果: " + countVowelsIterative(testStr) + "\n");

        System.out.println("=== 括號配對檢查 ===");
        System.out.println("遞迴結果: " + isBalancedRecursive(parens));
        System.out.println("迭代結果: " + isBalancedIterative(parens));
    }
}