import java.util.Arrays;

public class AdvancedArrayRecursion {

    // 1. 遞迴實作快速排序
    public static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(arr, left, right);
            quickSort(arr, left, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, right);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, right);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 2. 遞迴合併兩個已排序的陣列
    public static int[] mergeSortedArrays(int[] a, int[] b) {
        return mergeHelper(a, 0, b, 0, new int[a.length + b.length], 0);
    }

    private static int[] mergeHelper(int[] a, int i, int[] b, int j, int[] result, int k) {
        if (i == a.length) {
            while (j < b.length) result[k++] = b[j++];
            return result;
        }
        if (j == b.length) {
            while (i < a.length) result[k++] = a[i++];
            return result;
        }

        if (a[i] < b[j]) {
            result[k] = a[i];
            return mergeHelper(a, i + 1, b, j, result, k + 1);
        } else {
            result[k] = b[j];
            return mergeHelper(a, i, b, j + 1, result, k + 1);
        }
    }

    // 3. 遞迴尋找第 k 小元素（QuickSelect）
    public static int kthSmallest(int[] arr, int k) {
        return quickSelect(arr, 0, arr.length - 1, k - 1);
    }

    private static int quickSelect(int[] arr, int left, int right, int k) {
        if (left == right) return arr[left];

        int pivotIndex = partition(arr, left, right);

        if (k == pivotIndex) return arr[k];
        else if (k < pivotIndex) return quickSelect(arr, left, pivotIndex - 1, k);
        else return quickSelect(arr, pivotIndex + 1, right, k);
    }

    // 4. 遞迴檢查是否存在子序列總和等於目標值（Subset Sum）
    public static boolean subsetSum(int[] arr, int target) {
        return subsetSumHelper(arr, target, 0);
    }

    private static boolean subsetSumHelper(int[] arr, int target, int index) {
        if (target == 0) return true;
        if (index >= arr.length) return false;

        // 包含或不包含當前元素
        return subsetSumHelper(arr, target - arr[index], index + 1) ||
               subsetSumHelper(arr, target, index + 1);
    }

    // 主程式：示範各個功能
    public static void main(String[] args) {
        // QuickSort
        int[] quickSortArray = {5, 3, 8, 4, 2, 7, 1, 10};
        quickSort(quickSortArray, 0, quickSortArray.length - 1);
        System.out.println("QuickSort 結果: " + Arrays.toString(quickSortArray));

        // Merge Sorted Arrays
        int[] sortedA = {1, 3, 5};
        int[] sortedB = {2, 4, 6};
        int[] merged = mergeSortedArrays(sortedA, sortedB);
        System.out.println("合併結果: " + Arrays.toString(merged));

        // K-th Smallest
        int[] kthArray = {7, 10, 4, 3, 20, 15};
        int k = 3;
        int kth = kthSmallest(kthArray.clone(), k); // clone 避免破壞原陣列
        System.out.println("第 " + k + " 小元素: " + kth);

        // Subset Sum
        int[] subsetArray = {3, 34, 4, 12, 5, 2};
        int target = 9;
        boolean hasSubset = subsetSum(subsetArray, target);
        System.out.println("是否存在子序列總和為 " + target + "？" + hasSubset);
    }
}