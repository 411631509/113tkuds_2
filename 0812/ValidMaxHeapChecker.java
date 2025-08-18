public class ValidMaxHeapChecker {

    /** 是否為有效 Max Heap（空陣列或單一元素都視為 true） */
    public static boolean isValidMaxHeap(int[] a) {
        return firstViolationIndex(a) == -1;
    }

    /**
     * 找出第一個違規的「子節點」索引；若沒有違規，回傳 -1。
     * 規則：對所有父節點 i（0..(n-2)/2），需滿足
     * a[i] >= a[left] 以及（若存在）a[i] >= a[right]
     */
    public static int firstViolationIndex(int[] a) {
        if (a == null || a.length <= 1) return -1;

        int n = a.length;
        int lastParent = (n - 2) / 2; // 最後一個非葉子節點
        for (int i = 0; i <= lastParent; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && a[i] < a[left]) return left;
            if (right < n && a[i] < a[right]) return right;
        }
        return -1;
    }

    // 簡單示範
    public static void main(String[] args) {
        test(new int[]{100, 90, 80, 70, 60, 75, 65});      // true
        test(new int[]{100, 90, 80, 95, 60, 75, 65});      // false: 索引3
        test(new int[]{50});                               // true
        test(new int[]{});                                 // true
    }

    private static void test(int[] a) {
        int bad = firstViolationIndex(a);
        if (bad == -1) {
            System.out.println(java.util.Arrays.toString(a) + " -> true");
        } else {
            int parent = (bad - 1) / 2;
            System.out.println(java.util.Arrays.toString(a) + " -> false "
                    + "(索引" + bad + "的" + a[bad] + " 大於父節點索引" + parent + "的" + a[parent] + ")");
        }
    }
}