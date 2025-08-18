import java.util.*;

public class NumberArrayProcessor {
    
    /**
     * 移除陣列中的重複元素
     * @param arr 輸入陣列
     * @return 去除重複元素後的陣列
     */
    public static int[] removeDuplicates(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        
        Set<Integer> uniqueSet = new LinkedHashSet<>();
        for (int num : arr) {
            uniqueSet.add(num);
        }
        
        return uniqueSet.stream().mapToInt(Integer::intValue).toArray();
    }
    
    /**
     * 合併兩個已排序的陣列
     * @param arr1 第一個已排序陣列
     * @param arr2 第二個已排序陣列
     * @return 合併後的已排序陣列
     */
    public static int[] mergeSortedArrays(int[] arr1, int[] arr2) {
        if (arr1 == null) arr1 = new int[0];
        if (arr2 == null) arr2 = new int[0];
        
        int[] merged = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        
        // 合併兩個陣列，保持排序順序
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }
        
        // 複製剩餘元素
        while (i < arr1.length) {
            merged[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            merged[k++] = arr2[j++];
        }
        
        return merged;
    }
    
    /**
     * 找出陣列中出現頻率最高的元素
     * @param arr 輸入陣列
     * @return 出現頻率最高的元素，如果陣列為空則返回null
     */
    public static Integer findMostFrequent(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        
        // 計算每個元素的出現頻率
        for (int num : arr) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // 找出頻率最高的元素
        int mostFrequent = arr[0];
        int maxFrequency = frequencyMap.get(mostFrequent);
        
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mostFrequent = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        
        return mostFrequent;
    }
    
    /**
     * 將陣列分割成兩個相等（或近似相等）的子陣列
     * @param arr 輸入陣列
     * @return 包含兩個子陣列的二維陣列
     */
    public static int[][] splitArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[2][0];
        }
        
        int midPoint = (arr.length + 1) / 2; // 當長度為奇數時，第一個陣列較長
        
        int[] firstHalf = new int[midPoint];
        int[] secondHalf = new int[arr.length - midPoint];
        
        System.arraycopy(arr, 0, firstHalf, 0, midPoint);
        System.arraycopy(arr, midPoint, secondHalf, 0, arr.length - midPoint);
        
        return new int[][]{firstHalf, secondHalf};
    }
    
    /**
     * 工具方法：印出陣列內容
     */
    public static void printArray(int[] arr) {
        if (arr == null) {
            System.out.println("null");
            return;
        }
        System.out.println(Arrays.toString(arr));
    }
    
    /**
     * 工具方法：印出二維陣列內容
     */
    public static void printArrays(int[][] arrays) {
        if (arrays == null) {
            System.out.println("null");
            return;
        }
        for (int i = 0; i < arrays.length; i++) {
            System.out.println("Array " + (i + 1) + ": " + Arrays.toString(arrays[i]));
        }
    }
    
    // 測試主方法
    public static void main(String[] args) {
        System.out.println("=== NumberArrayProcessor 測試 ===\n");
        
        // 測試資料
        int[] testArray = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        int[] sortedArray1 = {1, 3, 5, 7, 9};
        int[] sortedArray2 = {2, 4, 6, 8, 10};
        
        System.out.println("原始陣列: " + Arrays.toString(testArray));
        System.out.println();
        
        // 測試1：移除重複元素
        System.out.println("1. 移除重複元素:");
        int[] unique = removeDuplicates(testArray);
        printArray(unique);
        System.out.println();
        
        // 測試2：合併已排序陣列
        System.out.println("2. 合併已排序陣列:");
        System.out.println("陣列1: " + Arrays.toString(sortedArray1));
        System.out.println("陣列2: " + Arrays.toString(sortedArray2));
        int[] merged = mergeSortedArrays(sortedArray1, sortedArray2);
        System.out.print("合併結果: ");
        printArray(merged);
        System.out.println();
        
        // 測試3：找出最高頻率元素
        System.out.println("3. 找出最高頻率元素:");
        Integer mostFrequent = findMostFrequent(testArray);
        System.out.println("最高頻率元素: " + mostFrequent);
        System.out.println();
        
        // 測試4：分割陣列
        System.out.println("4. 分割陣列:");
        int[][] split = splitArray(testArray);
        printArrays(split);
        
        // 測試奇數長度陣列分割
        System.out.println("\n測試奇數長度陣列分割:");
        int[] oddArray = {1, 2, 3, 4, 5};
        System.out.println("原始陣列: " + Arrays.toString(oddArray));
        int[][] oddSplit = splitArray(oddArray);
        printArrays(oddSplit);
    }
}