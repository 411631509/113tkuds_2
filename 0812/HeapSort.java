import java.util.*;

public class HeapSort {

    // Java 8 版 repeat
    private static String repeat(String s, int count) {
        StringBuilder sb = new StringBuilder(s.length() * count);
        for (int i = 0; i < count; i++) sb.append(s);
        return sb.toString();
    }

    // 向下調整函數
    private static void heapifyDown(int[] arr, int i, int heapSize) {
        int maxIdx = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < heapSize && arr[left] > arr[maxIdx]) maxIdx = left;
        if (right < heapSize && arr[right] > arr[maxIdx]) maxIdx = right;

        if (maxIdx != i) {
            swap(arr, i, maxIdx);
            heapifyDown(arr, maxIdx, heapSize);
        }
    }

    // 建立 Max Heap
    private static void buildMaxHeap(int[] arr) {
        int n = arr.length;
        for (int i = (n / 2) - 1; i >= 0; i--) heapifyDown(arr, i, n);
    }

    // 交換
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }

    // 主要排序（含說明輸出）
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        System.out.println("原始陣列: " + Arrays.toString(arr));
        System.out.println("\n=== 階段 1：建立 Max Heap ===");
        buildMaxHeap(arr);
        System.out.println("建立 Max Heap 後: " + Arrays.toString(arr));

        System.out.println("\n=== 階段 2：排序過程 ===");
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i);
            System.out.println("將最大值 " + arr[i] + " 移到位置 " + i + ": " + Arrays.toString(arr));
            heapifyDown(arr, 0, i);
            System.out.println("重新調整後: " + Arrays.toString(arr));
        }
    }

    // 視覺化展示（先調整再印出；標示：堆積 | 已排序）
    public static void visualHeapSort(int[] arr) {
        System.out.println("=== 視覺化 Heap Sort 過程 ===");
        System.out.println("原始: " + Arrays.toString(arr));

        buildMaxHeap(arr);
        System.out.println("建堆: " + Arrays.toString(arr));

        int n = arr.length;
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapifyDown(arr, 0, i); // 先調整

            // 堆積部分 | 已排序部分
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int j = 0; j < i; j++) {
                sb.append(arr[j]);
                if (j < i - 1) sb.append(", ");
            }
            sb.append("] | [");
            for (int j = i; j < n; j++) {
                sb.append(arr[j]);
                if (j < n - 1) sb.append(", ");
            }
            sb.append("]");

            System.out.println("步驟 " + (n - i) + ": " + sb.toString());
        }
    }

    // 安靜版排序（效能比較用）
    private static void heapSortQuiet(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        buildMaxHeap(arr);
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapifyDown(arr, 0, i);
        }
    }

    // 比較不同排序演算法
    public static void compareWithOtherSorts() {
        System.out.println("\n=== 排序演算法比較 ===");
        int[] testData = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("Heap Sort:");
        int[] heapSortArr = testData.clone();
        long startTime = System.nanoTime();
        heapSortQuiet(heapSortArr); // 用安靜版避免 println 影響時間
        long heapSortTime = System.nanoTime() - startTime;
        System.out.println("結果: " + Arrays.toString(heapSortArr));

        System.out.println("\nArrays.sort():");
        int[] javaArr = testData.clone();
        startTime = System.nanoTime();
        Arrays.sort(javaArr);
        long javaSortTime = System.nanoTime() - startTime;
        System.out.println("結果: " + Arrays.toString(javaArr));

        System.out.println("\n時間比較 (nanoseconds):");
        System.out.println("Heap Sort: " + heapSortTime);
        System.out.println("Arrays.sort(): " + javaSortTime);

        System.out.println("\n特性比較:");
        System.out.println("Heap Sort - 時間複雜度: O(n log n) 最好/平均/最壞都相同");
        System.out.println("Heap Sort - 空間複雜度: O(1) 原地排序");
        System.out.println("Heap Sort - 穩定性: 不穩定");
        System.out.println("Arrays.sort() - 針對不同類型資料有最佳化");
    }

    public static void main(String[] args) {
        int[] arr1 = {12, 11, 13, 5, 6, 7};
        heapSort(arr1.clone());

        System.out.println("\n" + repeat("=", 50)); // Java 8 相容

        int[] arr2 = {4, 10, 3, 5, 1};
        visualHeapSort(arr2.clone());

        compareWithOtherSorts();
    }
}