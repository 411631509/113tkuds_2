import java.io.*;
import java.util.*;

public class M11_HeapSortWithTie {

    // -------- Fast I/O --------
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        int nextInt() throws IOException {
            int c, s = 1, x = 0;
            while ((c = read()) <= ' ') if (c == -1) return Integer.MIN_VALUE;
            if (c == '-') { s = -1; c = read(); }
            while (c > ' ') { x = x * 10 + (c - '0'); c = read(); }
            return x * s;
        }
    }

    // 封裝 (score, index)
    static class Pair {
        int score, idx;
        Pair(int s, int i) { score = s; idx = i; }
    }

    // 比大小：回傳 true 表示 a「比較大」（用於 Max-Heap）
    // 規則：分數大的比較大；分數相同時，索引大的比較大
    // 這樣在 heapsort 末端放「最大」元素時，可得到遞增且同分依索引遞增
    static boolean greater(Pair a, Pair b) {
        if (a.score != b.score) return a.score > b.score;
        return a.idx > b.idx;
    }

    static void heapify(Pair[] a) {
        for (int i = (a.length >>> 1) - 1; i >= 0; i--) siftDown(a, i, a.length);
    }

    static void siftDown(Pair[] a, int i, int n) {
        while (true) {
            int l = (i << 1) + 1;
            if (l >= n) break;
            int r = l + 1, best = l;
            if (r < n && greater(a[r], a[l])) best = r;
            if (greater(a[best], a[i])) {
                Pair tmp = a[i]; a[i] = a[best]; a[best] = tmp;
                i = best;
            } else break;
        }
    }

    static void heapSortAsc(int[] s) {
        int n = s.length;
        Pair[] a = new Pair[n];
        for (int i = 0; i < n; i++) a[i] = new Pair(s[i], i);

        // 建 Max-Heap
        heapify(a);

        // 反覆把「最大」丟到尾端 -> 得到遞增序（含平手規則）
        for (int end = n - 1; end > 0; end--) {
            Pair tmp = a[0]; a[0] = a[end]; a[end] = tmp;
            siftDown(a, 0, end);
        }

        // 回寫排序後的分數（遞增；同分依輸入索引遞增）
        for (int i = 0; i < n; i++) s[i] = a[i].score;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = fs.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = fs.nextInt();

        heapSortAsc(arr);

        for (int i = 0; i < n; i++) {
            if (i > 0) out.print(' ');
            out.print(arr[i]);
        }
        out.println();
        out.flush();
    }

    /*
     * Time Complexity: O(n log n)
     * 說明: 以 Max-Heap 建堆 O(n)，之後進行 n-1 次下沉與交換，每次 O(log n)，
     *      總計 O(n log n)。同分時以索引決定大小（索引大者視為「較大」），
     *      因而最終輸出為遞增且同分依輸入順序（索引遞增）。
     */
}
