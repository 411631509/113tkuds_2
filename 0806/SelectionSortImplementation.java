import java.util.Arrays;

public class SelectionSortImplementation {

    // 選擇排序
    public static void selectionSort(int[] array) {
        int n = array.length;
        int comparisons = 0;
        int swaps = 0;

        System.out.println("=== 選擇排序過程 ===");

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }

            if (i != minIndex) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
                swaps++;
            }

            System.out.println("第 " + (i + 1) + " 輪: " + Arrays.toString(array));
        }

        System.out.println("總比較次數: " + comparisons);
        System.out.println("總交換次數: " + swaps);
        System.out.println();
    }

    // 氣泡排序
    public static void bubbleSort(int[] array) {
        int n = array.length;
        int comparisons = 0;
        int swaps = 0;

        System.out.println("=== 氣泡排序過程 ===");

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                comparisons++;
                if (array[j] > array[j + 1]) {
                    // 交換
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }

            System.out.println("第 " + (i + 1) + " 輪: " + Arrays.toString(array));

            if (!swapped) break;
        }

        System.out.println("總比較次數: " + comparisons);
        System.out.println("總交換次數: " + swaps);
        System.out.println();
    }

    // 主程式：執行比較
    public static void main(String[] args) {
        int[] originalArray = {29, 10, 14, 37, 13};

        System.out.println("原始陣列: " + Arrays.toString(originalArray));
        System.out.println();

        // 執行選擇排序
        int[] selectionArray = Arrays.copyOf(originalArray, originalArray.length);
        selectionSort(selectionArray);

        // 執行氣泡排序
        int[] bubbleArray = Arrays.copyOf(originalArray, originalArray.length);
        bubbleSort(bubbleArray);
    }
}