import java.util.*;

public class KthSmallestElement {

    // 方法1：K 大小的 Max-Heap
    public static int kthSmallestMaxHeap(int[] a, int k) {
        PriorityQueue<Integer> max = new PriorityQueue<>(Comparator.reverseOrder());
        for (int v : a) {
            if (max.size() < k) max.offer(v);
            else if (v < max.peek()) { max.poll(); max.offer(v); }
        }
        return max.peek();
    }

    // 方法2：Min-Heap 取 K 次
    public static int kthSmallestMinHeap(int[] a, int k) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        for (int v : a) min.offer(v);
        int ans = -1;
        for (int i = 0; i < k; i++) ans = min.poll();
        return ans;
    }

    public static void main(String[] args) {
        test(new int[]{7,10,4,3,20,15}, 3, 7);
        test(new int[]{1}, 1, 1);
        test(new int[]{3,1,4,1,5,9,2,6}, 4, 3);

        // 粗略比較
        Random rnd = new Random(1);
        int[] big = new int[200000];
        for (int i=0;i<big.length;i++) big[i] = rnd.nextInt();
        int k = 1000;

        long t1 = System.nanoTime();
        kthSmallestMaxHeap(big, k);
        long t2 = System.nanoTime();
        kthSmallestMinHeap(big, k);
        long t3 = System.nanoTime();
        System.out.println("MaxHeap法(ns): " + (t2 - t1));
        System.out.println("MinHeap法(ns): " + (t3 - t2));
    }

    private static void test(int[] a, int k, int expect) {
        int x1 = kthSmallestMaxHeap(a, k);
        int x2 = kthSmallestMinHeap(a, k);
        System.out.println(Arrays.toString(a) + ", K=" + k +
                " -> " + x1 + " / " + x2 + " (expect " + expect + ")");
    }
}